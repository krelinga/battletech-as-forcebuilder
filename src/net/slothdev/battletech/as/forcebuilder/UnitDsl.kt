package net.slothdev.battletech.`as`.forcebuilder

class UnitDslException(missingField: String) : RuntimeException("missing field $missingField")

data class UnitDslDamageTuple(val short: Damage, val medium: Damage, val long: Damage)

data class UnitDsl(private val unitDb: UnitDb, private val theFamily: String? = null,
                   private val theGeneration: String? = null,
                   private val theVariant: String? = null, private val theSize: Int? = null,
                   private val theTargetMovementModifier: Int? = null,
                   private val theMovement: Int? = null,
                   private val theMovementJumping: Int? = null, private val theRole: Role? = null,
                   private val theDamage: UnitDslDamageTuple? = null,
                   private val theOverheat: Int? = null, private val theArmor: Int? = null,
                   private val theStructure: Int? = null) {
    // Constants to make the DSL code a little more terse.
    // Damage
    val min = Damage.minimal()
    // Role
    val brawler = Role.BRAWLER
    val sniper = Role.SNIPER
    val skirmisher = Role.SKIRMISHER
    val missileBoat = Role.MISSILE_BOAT
    val striker = Role.STRIKER

    fun family(x: String): UnitDsl {
        require(theFamily == null)
        return copy(theFamily = x)
    }

    fun family(x: String, ext: UnitDsl.() -> Unit) {
        family(x).ext()
    }

    fun generation(x: String): UnitDsl {
        require(theGeneration == null)
        return copy(theGeneration = x)
    }

    fun generation(x: String, ext: UnitDsl.() -> Unit) {
        return generation(x).ext()
    }

    fun variant(x: String): UnitDsl {
        require(theVariant == null)
        return copy(theVariant = x)
    }

    fun variant(x: String, ext: UnitDsl.() -> Unit) {
        variant(x).ext()
    }

    fun size(x: Int): UnitDsl {
        require(theSize == null)
        return copy(theSize = x)
    }

    fun size(x: Int, ext: UnitDsl.() -> Unit) {
        size(x).ext()
    }

    fun tmm(x: Int): UnitDsl {
        require(theTargetMovementModifier == null)
        return copy(theTargetMovementModifier = x)
    }

    fun tmm(x: Int, ext: UnitDsl.() -> Unit) {
        return tmm(x).ext()
    }

    fun mv(x: Int): UnitDsl {
        require(theMovement == null)
        return copy(theMovement = x)
    }

    fun mv(x: Int, ext: UnitDsl.() -> Unit) {
        mv(x).ext()
    }

    fun mvj(x: Int): UnitDsl {
        require(theMovementJumping == null)
        return copy(theMovementJumping = x)
    }

    fun mvj(x: Int, ext: UnitDsl.() -> Unit) {
        mvj(x).ext()
    }

    fun role(x: Role): UnitDsl {
        require(theRole == null)
        return copy(theRole = x)
    }

    fun role(x: Role, ext: UnitDsl.() -> Unit) {
        role(x).ext()
    }

    fun damage(s: Damage, m: Int, l: Int): UnitDsl {
        require(theDamage == null)
        return copy(theDamage = UnitDslDamageTuple(short = s, medium = Damage(m), long = Damage(l)))
    }

    fun damage(s: Int, m: Int, l: Int): UnitDsl {
        return damage(Damage(s), m, l)
    }

    fun damage(s: Damage, m: Int, l: Int, ext: UnitDsl.() -> Unit) {
        damage(s, m, l).ext()
    }

    fun damage(s: Int, m: Int, l: Int, ext: UnitDsl.() -> Unit) {
        damage(Damage(s), m, l).ext()
    }

    fun overheat(x: Int): UnitDsl {
        require(theOverheat == null)
        return copy(theOverheat = x)
    }

    fun overheat(x: Int, ext: UnitDsl.() -> Unit) {
        overheat(x).ext()
    }

    fun armor(x: Int): UnitDsl {
        require(theArmor == null)
        return copy(theArmor = x)
    }

    fun armor(x: Int, ext: UnitDsl.() -> Unit) {
        return armor(x).ext()
    }

    fun structure(x: Int): UnitDsl {
        require(theStructure == null)
        return copy(theStructure = x)
    }

    fun structure(x: Int, ext: UnitDsl.() -> Unit) {
        structure(x).ext()
    }

    fun unit(pv: Int) {
        val family = theFamily ?: throw UnitDslException("family")
        val generation = theGeneration ?: ""
        val variant = theVariant ?: throw UnitDslException("variant")
        val size = theSize ?: throw UnitDslException("size")
        val targetMovementModifier = theTargetMovementModifier ?: throw UnitDslException("tmm")
        val movement = theMovement ?: throw UnitDslException("mv")
        val movementJumping = theMovementJumping ?: 0
        val role = theRole ?: throw UnitDslException("role")
        val damage = theDamage ?: throw UnitDslException("damage")
        val overheat = theOverheat ?: 0
        val armor = theArmor ?: throw UnitDslException("armor")
        val structure = theStructure ?: throw UnitDslException("structure")

        val nameBuilder = StringBuilder()
        nameBuilder.append(family)
        if (generation != "") {
            nameBuilder.append(" ")
            nameBuilder.append(generation)
        }
        nameBuilder.append(" ")
        nameBuilder.append(variant)
        val name = nameBuilder.toString()

        unitDb.add(GameUnit(name, pv, size, targetMovementModifier, movement, movementJumping, role,
                            damage.short, damage.medium, damage.long, overheat, armor, structure))
    }
}