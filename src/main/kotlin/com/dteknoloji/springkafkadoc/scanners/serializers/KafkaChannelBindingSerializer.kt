package com.dteknoloji.springkafkadoc.scanners.serializers

import com.asyncapi.v2.binding.kafka.KafkaChannelBinding
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class KafkaChannelBindingSerializer(t: Class<KafkaChannelBinding>? = null) : StdSerializer<KafkaChannelBinding>(t) {

    override fun serialize(value: KafkaChannelBinding, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeStartObject()
        gen.writeEndObject()
    }
}
