package com.dteknoloji.springkafkadoc.configuration

import com.asyncapi.v2.binding.kafka.KafkaChannelBinding
import com.asyncapi.v2.binding.kafka.KafkaOperationBinding
import com.dteknoloji.springkafkadoc.scanners.serializers.KafkaChannelBindingSerializer
import com.dteknoloji.springkafkadoc.scanners.serializers.KafkaOperationBindingSerializer
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.swagger.oas.inflector.processors.JsonNodeExampleSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ObjectMapperConfiguration {

    @Bean
    fun jsonMapper() = ObjectMapper().apply {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        val module = SimpleModule()
        module.addSerializer(KafkaChannelBinding::class.java, KafkaChannelBindingSerializer())
        module.addSerializer(KafkaOperationBinding::class.java, KafkaOperationBindingSerializer())
        this.registerModules(module, JavaTimeModule())
    }

    @Bean
    fun objectMapper() = ObjectMapper().apply {
        this.registerModules(SimpleModule().addSerializer(JsonNodeExampleSerializer()), JavaTimeModule())
    }

    @Bean
    fun yamlMapper() = ObjectMapper(YAMLFactory().apply { disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER) }).apply {
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        val module = SimpleModule()
        module.addSerializer(KafkaChannelBinding::class.java, KafkaChannelBindingSerializer())
        module.addSerializer(KafkaOperationBinding::class.java, KafkaOperationBindingSerializer())
        this.registerModules(module, JavaTimeModule())
    }
}
