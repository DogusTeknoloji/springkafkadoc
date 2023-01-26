package com.dteknoloji.springkafkadoc.scanners.components

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.config.BeanDefinition
import java.util.Optional

abstract class AbstractClassPathComponentsScanner : ComponentsScanner {

    protected open fun filterBeanDefinitionsToClasses(beanDefinitions: Set<BeanDefinition>): Set<Class<*>> {
        return beanDefinitions.asSequence()
            .map { it.beanClassName }
            .map { className -> getClass(className!!) }
            .filter { it.isPresent }
            .filter { isSuitableComponent(it.get()) }
            .map { it.get() }
            .toSet()
    }

    protected open fun isSuitableComponent(clazz: Class<*>): Boolean {
        if (FactoryBean::class.java.isAssignableFrom(clazz)) {
            return false
        }

        return !clazz.isInterface
    }

    private fun getClass(className: String): Optional<Class<*>> {
        return try {
            Optional.of<Class<*>>(Class.forName(className))
        } catch (e: ClassNotFoundException) {
            Optional.empty<Class<*>>()
        }
    }
}
