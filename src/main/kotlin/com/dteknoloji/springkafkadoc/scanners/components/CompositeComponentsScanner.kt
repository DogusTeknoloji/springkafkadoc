package com.dteknoloji.springkafkadoc.scanners.components

open class CompositeComponentsScanner(vararg componentsScanners: ComponentsScanner?) : ComponentsScanner {

    private val componentsScanners: List<ComponentsScanner>

    init {
        val scannerList = mutableListOf<ComponentsScanner>()
        componentsScanners.forEach {
            it?.let { scanner -> scannerList.add(scanner) }
        }
        require(scannerList.isNotEmpty()) { "There must be at least one valid/non-null beans scanner given to the composite scanner" }
        this.componentsScanners = scannerList
    }

    override fun scanForComponents(): Set<Class<*>> {
        val components = hashSetOf<Class<*>>()
        componentsScanners.forEach {
            components.addAll(it.scanForComponents())
        }

        return components
    }
}
