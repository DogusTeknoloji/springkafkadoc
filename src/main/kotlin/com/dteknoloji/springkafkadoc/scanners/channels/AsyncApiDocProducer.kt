package com.dteknoloji.springkafkadoc.scanners.channels

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Target(ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
annotation class AsyncApiDocProducer(
    val topicPattern: String = "",
    val groupId: String = ""
)
