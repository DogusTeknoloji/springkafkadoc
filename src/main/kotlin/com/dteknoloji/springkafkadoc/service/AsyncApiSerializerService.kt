package com.dteknoloji.springkafkadoc.service

import com.asyncapi.v2.binding.kafka.KafkaChannelBinding
import com.asyncapi.v2.binding.kafka.KafkaOperationBinding
import com.dteknoloji.springkafkadoc.scanners.serializers.KafkaChannelBindingSerializer
import com.dteknoloji.springkafkadoc.scanners.serializers.KafkaOperationBindingSerializer
import com.dteknoloji.springkafkadoc.types.AsyncAPI
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.PrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import org.springframework.stereotype.Service

@Service
class AsyncApiSerializerService(
    private val jsonMapper: ObjectMapper,
    private val printer: PrettyPrinter
) {

    private val yamlMapper = ObjectMapper(YAMLFactory().apply { disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER) })

    init {
        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        val moduleJson = SimpleModule()
        moduleJson.addSerializer(KafkaChannelBinding::class.java, KafkaChannelBindingSerializer())
        moduleJson.addSerializer(KafkaOperationBinding::class.java, KafkaOperationBindingSerializer())
        jsonMapper.registerModule(moduleJson)

        yamlMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        val moduleYaml = SimpleModule()
        moduleYaml.addSerializer(KafkaChannelBinding::class.java, KafkaChannelBindingSerializer())
        moduleYaml.addSerializer(KafkaOperationBinding::class.java, KafkaOperationBindingSerializer())
        yamlMapper.registerModule(moduleYaml)
    }

    fun toJsonString(asyncAPI: AsyncAPI): String {
        return jsonMapper.writer(printer).writeValueAsString(asyncAPI)
    }

    fun toYamlString(asyncAPI: AsyncAPI): String {
        return yamlMapper.writer(printer).writeValueAsString(asyncAPI)
    }
}
