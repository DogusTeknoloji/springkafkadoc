package com.dteknoloji.springkafkadoc.types

import com.asyncapi.v2.model.Tag
import com.asyncapi.v2.model.channel.ChannelItem
import com.asyncapi.v2.model.info.Info
import com.asyncapi.v2.model.server.Server

class AsyncAPI private constructor(
    val asyncapi: String? = "2.0.0",
    val info: Info?,
    val defaultContentType: String?,
    val servers: Map<String, Server>?,
    val channels: Map<String, ChannelItem>?,
    val components: Components?,
    val tags: Set<Tag> = emptySet()
) {

    data class builder(
        var asyncapi: String? = "2.0.0",
        var info: Info? = null,
        var defaultContentType: String? = null,
        var servers: Map<String, Server>? = null,
        var channels: Map<String, ChannelItem>? = null,
        var components: Components? = null,
        var tags: Set<Tag> = emptySet()
    ) {

        fun asyncapi(asyncapi: String) = apply { this.asyncapi = asyncapi }
        fun info(info: Info?) = apply { this.info = info }
        fun defaultContentType(defaultContentType: String?) = apply { this.defaultContentType = defaultContentType }
        fun servers(servers: Map<String, Server>?) = apply { this.servers = servers }
        fun channels(channels: Map<String, ChannelItem>) = apply { this.channels = channels }
        fun components(components: Components?) = apply { this.components = components }
        fun tags(tags: Set<Tag>) = apply { this.tags = tags }
        fun build() = AsyncAPI(asyncapi, info, defaultContentType, servers, channels, components, tags)
    }
}
