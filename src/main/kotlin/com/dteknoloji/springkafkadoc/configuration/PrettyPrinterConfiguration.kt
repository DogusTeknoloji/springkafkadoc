package com.dteknoloji.springkafkadoc.configuration

import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class PrettyPrinterConfiguration {

    @Bean
    fun printer() = DefaultPrettyPrinter().withObjectIndenter(DefaultIndenter("  ", DefaultIndenter.SYS_LF))
}
