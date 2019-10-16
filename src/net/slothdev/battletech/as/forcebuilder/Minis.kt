package net.slothdev.battletech.`as`.forcebuilder

data class Unit(val name: String, val pv: Int) {
    init {
        require(pv >= 0) { "pv parameter must be >= 0, we saw $pv" }
    }
}

data class Miniature(val kind: String, val units: Set<Unit>) {
    init {
        require(units.isNotEmpty())
    }

    val pv: Int
        get() = units.first().pv

    // A shortcut to make a Miniature with a single unit.
    constructor(name: String, pv: Int = 0) : this(name, setOf(Unit(name, pv)))
}