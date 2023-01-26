package com.dteknoloji.springkafkadoc.types.message

import com.dteknoloji.springkafkadoc.types.message.header.HeaderReference

class Message private constructor(
    val name: String?,
    val title: String?,
    val description: String?,
    val payload: PayloadReference?,
    val headers: HeaderReference?
) {

    data class builder(
        var name: String? = null,
        var title: String? = null,
        var description: String? = null,
        var payload: PayloadReference? = null,
        var headers: HeaderReference? = null
    ) {

        fun name(name: String) = apply { this.name = name }
        fun title(title: String) = apply { this.title = title }
        fun description(description: String?) = apply { this.description = description }
        fun payload(payload: PayloadReference) = apply { this.payload = payload }
        fun headers(headers: HeaderReference) = apply { this.headers = headers }
        fun build() = Message(name, title, description, payload, headers)
    }
}
