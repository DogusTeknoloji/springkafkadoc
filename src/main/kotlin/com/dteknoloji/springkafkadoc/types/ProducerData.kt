package com.dteknoloji.springkafkadoc.types

import com.asyncapi.v2.binding.ChannelBinding
import com.asyncapi.v2.binding.OperationBinding
import com.dteknoloji.springkafkadoc.types.message.header.AsyncHeaders

class ProducerData private constructor(
    val channelName: String?,
    val description: String?,
    val channelBinding: Map<String, ChannelBinding>?,
    val payloadType: Class<*>?,
    val operationBinding: Map<String, OperationBinding>?,
    val headers: AsyncHeaders = AsyncHeaders.NOT_DOCUMENTED
) {

    data class builder(
        var channelName: String? = null,
        var description: String? = null,
        var channelBinding: Map<String, ChannelBinding>? = null,
        var payloadType: Class<*>? = null,
        var operationBinding: Map<String, OperationBinding>? = null
    ) {

        fun channelName(channelName: String) = apply { this.channelName = channelName }
        fun description(description: String) = apply { this.description = description }
        fun channelBinding(channelBinding: Map<String, ChannelBinding>) = apply { this.channelBinding = channelBinding }
        fun payloadType(payloadType: Class<*>) = apply { this.payloadType = payloadType }
        fun operationBinding(operationBinding: Map<String, OperationBinding>) = apply { this.operationBinding = operationBinding }
        fun build() = ProducerData(channelName, description, channelBinding, payloadType, operationBinding)
    }
}
