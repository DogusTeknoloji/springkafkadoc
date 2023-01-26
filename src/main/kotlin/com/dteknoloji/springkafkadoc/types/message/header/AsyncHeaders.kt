package com.dteknoloji.springkafkadoc.types.message.header

import io.swagger.v3.oas.models.media.Schema
import java.util.TreeMap

class AsyncHeaders(val schemaName: String) : TreeMap<String, Schema<*>>() {

    companion object {

        val NOT_DOCUMENTED = AsyncHeaders("HeadersNotDocumented")
    }
}
