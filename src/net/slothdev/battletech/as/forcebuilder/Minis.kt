package net.slothdev.battletech.`as`.forcebuilder

enum class Role {
    BRAWLER,
}

class Damage private constructor(val value: Int, val minimal: Boolean) {
    init {
        require(value >= 0)
        require(!minimal || value == 0)
    }

    constructor(value: Int) : this(value, false)

    companion object {
        fun minimal() = Damage(0, true)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Damage

        if (value != other.value) return false
        if (minimal != other.minimal) return false

        return true
    }

    override fun hashCode(): Int {
        var result = value
        result = 31 * result + minimal.hashCode()
        return result
    }
}

data class Unit(val name: String, val pointValue: Int, val size: Int = 0,
                val targetMovementModifier: Int = 0, val movement: Int = 0,
                val movementJumping: Int = 0, val role: Role = Role.BRAWLER,
                val damageShort: Damage = Damage(0), val damageMedium: Damage = Damage(0),
                val damageLong: Damage = Damage(0), val overheat: Int = 0,
                val armor: Int = 0, val structure: Int = 0) {
    init {
        require(pointValue >= 0) { "pv parameter must be >= 0, we saw $pointValue" }
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

enum class Color {
    BROWN, GREEN, UNPAINTED_GRAY_PLASTIC, UNPAINTED_PEWTER, BLACK,
}

data class Miniature(val kind: String, val primaryColor: Color, val supportedUnits: Set<Unit>) {
    init {
        require(supportedUnits.isNotEmpty())
    }

    // Shortcut for miniatures composed of a single unit.
    constructor(primaryColor: Color, unit: Unit) : this(unit.name, primaryColor, setOf(unit))
}

// only open to be used with GlobalMiniatureDb
open class MiniatureDb {
    private val miniatureSet: MutableSet<Miniature> = mutableSetOf()
    private val kindSet: MutableSet<String> = mutableSetOf()

    fun add(miniature: Miniature) {
        require(!kindSet.contains(miniature.kind)) { miniature.kind }
        kindSet.add(miniature.kind)
        miniatureSet.add(miniature)
    }

    fun asSet(): Set<Miniature> = miniatureSet
}