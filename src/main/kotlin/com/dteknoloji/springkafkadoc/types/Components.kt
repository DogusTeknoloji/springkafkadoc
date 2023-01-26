package com.dteknoloji.springkafkadoc.types

import io.swagger.v3.oas.models.media.Schema

class Components private constructor(
    val schemas: Map<String, Schema<*>>?
) {

    data class builder(
        var schemas: Map<String, Schema<*>>? = null
    ) {

        fun schemas(schemas: Map<String, Schema<*>>) = apply { this.schemas = schemas }
        fun build() = Components(schemas)
    }
}
