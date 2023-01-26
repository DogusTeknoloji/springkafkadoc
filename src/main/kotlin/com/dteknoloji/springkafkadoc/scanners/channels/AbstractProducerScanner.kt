package com.dteknoloji.springkafkadoc.scanners.channels

import com.asyncapi.v2.binding.ChannelBinding
import com.asyncapi.v2.binding.OperationBinding
import com.asyncapi.v2.model.channel.ChannelItem
import com.asyncapi.v2.model.channel.operation.Operation
import com.dteknoloji.springkafkadoc.configuration.AsyncApiDocket
import com.dteknoloji.springkafkadoc.service.SchemasService
import com.dteknoloji.springkafkadoc.types.message.Message
import com.dteknoloji.springkafkadoc.types.message.PayloadReference
import com.dteknoloji.springkafkadoc.types.message.header.AsyncHeaders
import com.dteknoloji.springkafkadoc.types.message.header.HeaderReference
import com.google.common.collect.Maps
import java.lang.reflect.Method
import java.util.Optional

abstract class AbstractProducerScanner<T : Annotation>(
    private val docket: AsyncApiDocket,
    private val schemasService: SchemasService
) : ChannelsScanner {

    override fun scan(): Map<String, ChannelItem> {
        return docket.producerComponentsScanner?.scanForComponents()
            ?.map { getAnnotatedMethods(it) }
            ?.flatMap { it.toSet() }
            ?.map { mapMethodToChannel(it) }
            ?.associateBy({ (key, _) -> key }, { (_, value) -> value })
            ?: emptyMap()
    }

    private fun getAnnotatedMethods(type: Class<*>): Set<Method> {
        val annotationClass = getProducerAnnotationClass()
        return type.declaredMethods.filter { method -> method.isAnnotationPresent(annotationClass) }.toSet()
    }

    private fun mapMethodToChannel(method: Method): Map.Entry<String, ChannelItem> {
        val listenerAnnotationClass = getProducerAnnotationClass()
        val annotation = Optional.of(method.getAnnotation(listenerAnnotationClass))
            .orElseThrow { IllegalArgumentException("Method must be annotated with " + listenerAnnotationClass.name) }
        val channelName = getChannelName(annotation)
        val channelBinding = buildChannelBinding(annotation)
        val operationBinding = buildOperationBinding(annotation)
        val payload = getPayloadType(method)
        val operationId = channelName + "_" + METHOD_NAME
        val channel = buildChannel(channelBinding, payload, operationBinding, operationId)
        return Maps.immutableEntry(channelName, channel)
    }

    private fun buildChannel(
        channelBinding: Map<String, ChannelBinding?>,
        payloadType: Class<*>,
        operationBinding: Map<String, OperationBinding?>,
        operationId: String
    ): ChannelItem {
        val modelName = schemasService.register(payloadType)
        val headerModelName = schemasService.register(AsyncHeaders.NOT_DOCUMENTED)

        val message = Message.builder()
            .name(payloadType.name.split(".").last())
            .title(modelName)
            .payload(PayloadReference.fromModelName(modelName))
            .headers(HeaderReference.fromModelName(headerModelName))
            .build()

        val operation = Operation.builder()
            .description("Auto-generated description")
            .operationId(operationId)
            .message(message)
            .bindings(operationBinding)
            .build()

        return ChannelItem.builder()
            .bindings(channelBinding)
            .subscribe(operation)
            .build()
    }

    private companion object {
        const val METHOD_NAME = "send"
    }

    protected abstract fun getProducerAnnotationClass(): Class<T>

    protected abstract fun getChannelName(annotation: T): String

    protected abstract fun buildChannelBinding(annotation: T): Map<String, ChannelBinding?>

    protected abstract fun buildOperationBinding(annotation: T): Map<String, OperationBinding?>

    protected abstract fun getPayloadType(method: Method): Class<*>
}
