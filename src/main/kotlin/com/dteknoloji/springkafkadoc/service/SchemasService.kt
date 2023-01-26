package com.dteknoloji.springkafkadoc.service

import com.dteknoloji.springkafkadoc.types.message.header.AsyncHeaders
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.oas.inflector.examples.ExampleBuilder
import io.swagger.v3.core.converter.ModelConverters
import io.swagger.v3.oas.models.media.MapSchema
import io.swagger.v3.oas.models.media.ObjectSchema
import io.swagger.v3.oas.models.media.Schema
import io.swagger.v3.oas.models.media.StringSchema
import org.springframework.stereotype.Service

@Service
class SchemasService(
    private val converter: ModelConverters,
    private val objectMapper: ObjectMapper
) {

    private val definitions = mutableMapOf<String, Schema<*>>()

    fun getDefinitions(): Map<String, Schema<*>> {
        definitions.forEach { (_, schema) -> buildExampleAsString(schema) }
        definitions.forEach { (_, schema) -> deserializeExampleToMap(schema) }

        return definitions
    }

    fun register(type: Class<*>): String {
        val schemas = converter.readAll(type)
        definitions.putAll(schemas)

        if (schemas.isEmpty() && type == String::class.java) {
            definitions["String"] = StringSchema()
            return "String"
        }

        if (type == List::class.java) {
            definitions["List"] = ObjectSchema()
            return "List"
        }

        if (schemas.size == 1) {
            return schemas.keys.first()
        }

        val resolvedPayloadModelName = converter.read(type).keys

        return if (resolvedPayloadModelName.isNotEmpty()) resolvedPayloadModelName.first() else type.simpleName
    }

    fun register(headers: AsyncHeaders): String {
        val headerSchema = MapSchema()
        headerSchema.properties(headers)
        definitions[headers.schemaName] = headerSchema

        return headers.schemaName
    }

    private fun buildExampleAsString(schema: Schema<*>) {
        val example = ExampleBuilder.fromSchema(schema, definitions)
        val exampleAsJson = objectMapper.writeValueAsString(example)
        schema.example = exampleAsJson
    }

    private fun deserializeExampleToMap(schema: Schema<*>) {
        schema.example = objectMapper.readValue(schema.example as? String, MutableMap::class.java)
    }
}
