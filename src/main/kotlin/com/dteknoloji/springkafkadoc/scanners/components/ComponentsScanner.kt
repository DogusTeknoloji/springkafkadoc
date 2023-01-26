package com.dteknoloji.springkafkadoc.scanners.components

interface ComponentsScanner {

    fun scanForComponents(): Set<Class<*>>
}
