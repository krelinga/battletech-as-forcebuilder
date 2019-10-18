package net.slothdev.battletech.`as`.forcebuilder

data class Unit(val name: String, val pv: Int) {
    init {
        require(pv >= 0) { "pv parameter must be >= 0, we saw $pv" }
    }
}

class UnitDb {
    private val unitSet: MutableSet<Unit> = mutableSetOf()
    private val nameSet: MutableSet<String> = mutableSetOf()

    fun add(unit: Unit) {
        require(!nameSet.contains(unit.name))
        nameSet.add(unit.name)
        unitSet.add(unit)
    }

    fun asSet() = unitSet
}

data class Miniature(val kind: String, val supportedUnits: Set<Unit>) {
    init {
        require(supportedUnits.isNotEmpty())
    }

    // Shortcut for miniatures composed of a single unit.
    constructor(unit: Unit) : this(unit.name, setOf(unit))
}