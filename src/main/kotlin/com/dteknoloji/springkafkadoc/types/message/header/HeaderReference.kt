package com.dteknoloji.springkafkadoc.types.message.header

data class HeaderReference(val `$ref`: String) {

    companion object {

        fun fromModelName(modelName: String) = HeaderReference("#/components/schemas/$modelName")
    }
}
