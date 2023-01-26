package com.dteknoloji.springkafkadoc.scanners.components

import org.apache.commons.lang3.StringUtils
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider
import org.springframework.core.type.filter.AnnotationTypeFilter
import org.springframework.stereotype.Component

class ComponentComponentsScanner(private val basePackage: String) : AbstractClassPathComponentsScanner() {

    init {
        require(!StringUtils.isEmpty(basePackage)) { "There must be a non-empty basePackage given" }
    }

    override fun scanForComponents(): Set<Class<*>> {
        val provider = ClassPathScanningCandidateComponentProvider(false)
        provider.addIncludeFilter(AnnotationTypeFilter(Component::class.java))

        return filterBeanDefinitionsToClasses(provider.findCandidateComponents(basePackage))
    }
}
