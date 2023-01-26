package com.dteknoloji.springkafkadoc.scanners.serializers

import com.asyncapi.v2.binding.kafka.KafkaOperationBinding
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class KafkaOperationBindingSerializer(t: Class<KafkaOperationBinding>? = null) : StdSerializer<KafkaOperationBinding>(t) {

    override fun serialize(value: KafkaOperationBinding, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        value.groupId?.let { writeGroupId(value, gen) }
        gen.writeEndObject()
    }

    private fun writeGroupId(value: KafkaOperationBinding, gen: JsonGenerator) {
        gen.writeFieldName("groupId")
        gen.writeStartObject()
        gen.writeStringField("type", "string")
        gen.writeArrayFieldStart("enum")
        gen.writeString(value.groupId as String?)
        gen.writeEndArray()
        gen.writeEndObject()
    }
}
