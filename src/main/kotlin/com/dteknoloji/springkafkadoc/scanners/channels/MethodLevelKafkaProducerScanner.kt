package com.dteknoloji.springkafkadoc.scanners.channels

import com.asyncapi.v2.binding.ChannelBinding
import com.asyncapi.v2.binding.OperationBinding
import com.asyncapi.v2.binding.kafka.KafkaChannelBinding
import com.asyncapi.v2.binding.kafka.KafkaOperationBinding
import com.dteknoloji.springkafkadoc.configuration.AsyncApiDocket
import com.dteknoloji.springkafkadoc.service.SchemasService
import com.google.common.collect.ImmutableMap
import org.springframework.context.EmbeddedValueResolverAware
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Service
import org.springframework.util.StringValueResolver
import java.lang.reflect.Method

@Service
class MethodLevelKafkaProducerScanner(
    docket: AsyncApiDocket,
    schemasService: SchemasService
) : AbstractProducerScanner<AsyncApiDocProducer>(docket, schemasService), ChannelsScanner, EmbeddedValueResolverAware {

    private var resolver: StringValueResolver? = null

    override fun setEmbeddedValueResolver(resolver: StringValueResolver) {
        this.resolver = resolver
    }

    override fun getProducerAnnotationClass(): Class<AsyncApiDocProducer> {
        return AsyncApiDocProducer::class.java
    }

    override fun getChannelName(annotation: AsyncApiDocProducer): String {
        return annotation.topicPattern + "_subscribe"
    }

    override fun buildChannelBinding(annotation: AsyncApiDocProducer): Map<String, ChannelBinding?> {
        return ImmutableMap.of("kafka", KafkaChannelBinding())
    }

    override fun buildOperationBinding(annotation: AsyncApiDocProducer): Map<String, OperationBinding> {
        var groupId = resolver!!.resolveStringValue(annotation.groupId)
        if (groupId == null || groupId.isEmpty()) {
            groupId = null
        }
        val binding = KafkaOperationBinding()
        binding.groupId = groupId
        return ImmutableMap.of("kafka", binding)
    }

    override fun getPayloadType(method: Method): Class<*> {
        val methodName = String.format("%s::%s", method.declaringClass.simpleName, METHOD_NAME)
        val parameterTypes = method.parameterTypes
        return when (parameterTypes.size) {
            0 -> throw IllegalArgumentException("Listener methods must not have 0 parameters: $methodName")
            1 -> parameterTypes[0]
            else -> getPayloadType(parameterTypes, method.parameterAnnotations, methodName)
        }
    }

    private fun getPayloadType(parameterTypes: Array<Class<*>>, parameterAnnotations: Array<Array<Annotation>>, methodName: String): Class<*> {
        val payloadAnnotatedParameterIndex = getPayloadAnnotatedParameterIndex(parameterAnnotations)
        if (payloadAnnotatedParameterIndex == -1) {
            val msg = """"Multi-parameter AsyncApiProducer methods must have one parameter annotated with @Payload, but none was found: $methodName"""
            throw IllegalArgumentException(msg)
        }
        return parameterTypes[payloadAnnotatedParameterIndex]
    }

    private fun getPayloadAnnotatedParameterIndex(parameterAnnotations: Array<Array<Annotation>>): Int {
        parameterAnnotations.forEachIndexed { index, annotations ->
            if (annotations.any { annotation -> annotation is Payload }) return index
        }
        return -1
    }

    private companion object {
        const val METHOD_NAME = "send"
    }
}
