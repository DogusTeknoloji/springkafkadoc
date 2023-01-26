package com.dteknoloji.springkafkadoc.service

import com.dteknoloji.springkafkadoc.types.AsyncAPI
import com.fasterxml.jackson.core.PrettyPrinter
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service

@Service
class AsyncApiSerializerService(
    private val jsonMapper: ObjectMapper,
    private val yamlMapper: ObjectMapper,
    private val printer: PrettyPrinter
) {

    fun toJsonString(asyncAPI: AsyncAPI): String {
        return jsonMapper.writer(printer).writeValueAsString(asyncAPI)
    }

    fun toYamlString(asyncAPI: AsyncAPI): String {
        return yamlMapper.writer(printer).writeValueAsString(asyncAPI)
    }
}
