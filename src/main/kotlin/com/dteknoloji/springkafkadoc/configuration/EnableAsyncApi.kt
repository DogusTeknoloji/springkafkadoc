package com.dteknoloji.springkafkadoc.configuration

import org.springframework.context.annotation.ComponentScan
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = [ElementType.TYPE])
@ComponentScan(basePackages = ["com/dteknoloji/springkafkadoc"])
annotation class EnableAsyncApi()
