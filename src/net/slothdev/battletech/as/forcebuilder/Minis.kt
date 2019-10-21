package net.slothdev.battletech.`as`.forcebuilder

enum class Role {
    BRAWLER,
}

data class Unit(val name: String, val pv: Int, val sz: Int = 0, val tmm: Int = 0, val mv: Int = 0,
                val mvj: Int = 0, val role: Role? = null, val dS: Int? = null, val dM: Int? = null,
                val dL: Int? = null, val ov: Int = 0, val a: Int = 0, val s: Int = 0) {
    init {
        require(pv >= 0) { "pv parameter must be >= 0, we saw $pv" }
    }
}

// only open so that GlobalUnitDb can inherit from this.
open class UnitDb {
    private val unitSet: MutableSet<Unit> = mutableSetOf()
    private val nameSet: MutableSet<String> = mutableSetOf()

    fun add(unit: Unit) {
        require(!nameSet.contains(unit.name)) {
            "The database already has a unit named ${unit.name}"
        }
        nameSet.add(unit.name)
        unitSet.add(unit)
    }

    fun asSet(): Set<Unit> = unitSet

    fun withNamePrefix(name: String): Set<Unit> {
        return asSet().filter { it.name.startsWith(name) }.toSet()
    }

}

data class Miniature(val kind: String, val supportedUnits: Set<Unit>) {
    init {
        require(supportedUnits.isNotEmpty())
    }

    // Shortcut for miniatures composed of a single unit.
    constructor(unit: Unit) : this(unit.name, setOf(unit))
}

// only open to be used with GlobalMiniatureDb
open class MiniatureDb {
    private val miniatureSet: MutableSet<Miniature> = mutableSetOf()
    private val kindSet: MutableSet<String> = mutableSetOf()

    fun add(miniature: Miniature) {
        require(!kindSet.contains(miniature.kind))
        kindSet.add(miniature.kind)
        miniatureSet.add(miniature)
    }

    fun asSet(): Set<Miniature> = miniatureSet
}