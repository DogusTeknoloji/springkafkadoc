package com.dteknoloji.springkafkadoc.types.message

data class PayloadReference(val `$ref`: String) {

    companion object {

        fun fromModelName(modelName: String) = PayloadReference("#/components/schemas/$modelName")
    }
}
