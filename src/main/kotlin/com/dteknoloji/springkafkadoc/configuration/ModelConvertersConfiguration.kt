package com.dteknoloji.springkafkadoc.configuration

import io.swagger.v3.core.converter.ModelConverters
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ModelConvertersConfiguration {

    @Bean
    fun converter(): ModelConverters = ModelConverters.getInstance()
}
