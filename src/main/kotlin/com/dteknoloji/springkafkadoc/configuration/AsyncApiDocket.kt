package com.dteknoloji.springkafkadoc.configuration

import com.asyncapi.v2.model.info.Info
import com.asyncapi.v2.model.server.Server
import com.dteknoloji.springkafkadoc.scanners.components.ComponentComponentsScanner
import com.dteknoloji.springkafkadoc.scanners.components.ComponentsScanner
import com.dteknoloji.springkafkadoc.types.ConsumerData
import com.dteknoloji.springkafkadoc.types.ProducerData

class AsyncApiDocket private constructor(
    val consumerComponentsScanner: ComponentsScanner?,
    val producerComponentsScanner: ComponentsScanner?,
    val info: Info?,
    val servers: Map<String, Server>?,
    val producers: List<ProducerData>?,
    val consumers: List<ConsumerData>?,
) {

    data class builder(
        var consumerComponentsScanner: ComponentsScanner? = null,
        var producerComponentsScanner: ComponentsScanner? = null,
        var info: Info? = null,
        var servers: Map<String, Server>? = null,
        var producers: List<ProducerData>? = null,
        var consumers: List<ConsumerData>? = null,
    ) {

        fun consumerBasePackage(path: String) = apply { this.consumerComponentsScanner = ComponentComponentsScanner(path) }
        fun producerBasePackage(path: String) = apply { this.producerComponentsScanner = ComponentComponentsScanner(path) }
        fun info(info: Info?) = apply { this.info = info }
        fun servers(servers: Map<String, Server>?) = apply { this.servers = servers }
        fun server(serverKey: String, serverValue: Server) = apply { this.servers = mapOf(Pair(serverKey, serverValue)) }
        fun producers(producers: List<ProducerData>?) = apply { this.producers = producers }
        fun producer(producer: ProducerData) = apply { this.producers = listOf(producer) }
        fun consumers(consumers: List<ConsumerData>?) = apply { this.consumers = consumers }
        fun consumer(consumer: ConsumerData) = apply { this.consumers = listOf(consumer) }
        fun build() = AsyncApiDocket(consumerComponentsScanner, producerComponentsScanner, info, servers, producers, consumers)
    }
}
