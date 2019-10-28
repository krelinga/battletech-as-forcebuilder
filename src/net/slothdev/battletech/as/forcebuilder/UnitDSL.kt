package net.slothdev.battletech.`as`.forcebuilder

data class UnitDslDamage(var s: Damage? = null, var m: Damage? = null, var l: Damage? = null)

data class UnitDslState(val d: UnitDslDamage = UnitDslDamage(), var sz: Int? = null,
                        var tmm: Int? = null, var mv: Int? = null, var mvj: Int? = null,
                        var ov: Int? = null, var a: Int? = null, var s: Int? = null)

interface UnitDslProperties {
    var sz: Int?
    var tmm: Int?
    var mv: Int?
    var mvj: Int?
    var role: Role?
    var dS: Damage?
    var dM: Damage?
    var dL: Damage?
    var ov: Int?
    var a: Int?
    var s: Int?
}

data class UnitDslContext(override var sz: Int? = null, override var tmm: Int? = null,
                          override var mv: Int? = null, override var mvj: Int? = null,
                          override var role: Role? = null, override var dS: Damage? = null,
                          override var dM: Damage? = null, override var dL: Damage? = null,
                          override var ov: Int? = null, override var a: Int? = null,
                          override var s: Int? = null) : UnitDslProperties {

    fun update(d: UnitDslDamage? = null, sz: Int? = null, tmm: Int? = null, mv: Int? = null,
               mvj: Int? = null, role: Role? = null, ov: Int? = null, a: Int? = null,
               s: Int? = null) {
        if (d != null) {
            this.dS = d.s ?: this.dS
            this.dM = d.m ?: this.dM
            this.dL = d.l ?: this.dL
        }
        this.sz = sz ?: this.sz
        this.tmm = tmm ?: this.tmm
        this.mv = mv ?: this.mv
        this.mvj = mvj ?: this.mvj
        this.role = role ?: this.role
        this.ov = ov ?: this.ov
        this.a = a ?: this.a
        this.s = s ?: this.s
    }
}

fun propertiesToUnit(family: String, generation: String, variant: String, pv: Int,
                     properties: UnitDslProperties): Unit {
    val nameParts = mutableListOf<String>()
    if (family.isNotEmpty()) {
        nameParts.add(family)
    }
    if (generation.isNotEmpty()) {
        nameParts.add(generation)
    }
    if (variant.isNotEmpty()) {
        nameParts.add(variant)
    }
    return Unit(nameParts.joinToString(" "), pv, size = properties.sz!!,
                targetMovementModifier = properties.tmm!!, movement = properties.mv!!,
                movementJumping = properties.mvj!!, role = properties.role!!,
                damageShort = properties.dS!!, damageMedium = properties.dM!!,
                damageLong = properties.dL!!, overheat = properties.ov!!,
                armor = properties.a!!,
                structure = properties.s!!)
}

open class UnitDslGenerationContext(protected val family: String, protected val generation: String,
                                    protected val baseContext: UnitDslContext,
                                    protected val unitDb: UnitDb) :
        UnitDslProperties by baseContext {
    val min = Damage.minimal()

    fun D(value: Int) = Damage(value)

    fun dmg(s: Damage? = null, m: Damage? = null, l: Damage? = null) = UnitDslDamage(s, m, l)
    fun dmg(s: Int? = null, m: Damage? = null, l: Damage? = null) =
            UnitDslDamage(if (s != null) Damage(s) else null, m, l)

    fun dmg(s: Damage? = null, m: Int? = null, l: Damage? = null) =
            UnitDslDamage(s, if (m != null) Damage(m) else null, l)

    fun dmg(s: Damage? = null, m: Damage? = null, l: Int? = null) =
            UnitDslDamage(s, m, if (l != null) Damage(l) else null)

    fun dmg(s: Int? = null, m: Int? = null, l: Damage? = null) =
            UnitDslDamage(if (s != null) Damage(s) else null, if (m != null) Damage(m) else null, l)

    fun dmg(s: Int? = null, m: Damage? = null, l: Int? = null) =
            UnitDslDamage(if (s != null) Damage(s) else null, m, if (l != null) Damage(l) else null)

    fun dmg(s: Damage? = null, m: Int? = null, l: Int? = null) =
            UnitDslDamage(s, if (m != null) Damage(m) else null, if (l != null) Damage(l) else null)

    fun dmg(s: Int? = null, m: Int? = null, l: Int? = null) =
            UnitDslDamage(if (s != null) Damage(s) else null, if (m != null) Damage(m) else null,
                          if (l != null) Damage(l) else null)

    fun variant(name: String, pv: Int, builder: UnitDslContext.() -> kotlin.Unit) {
        val context = baseContext.copy()
        context.builder()

        unitDb.add(propertiesToUnit(family, generation, name, pv, context))
    }

    fun variant(pv: Int, builder: UnitDslContext.() -> kotlin.Unit) {
        variant("", pv, builder)
    }

    fun variant(name: String, pv: Int, d: UnitDslDamage? = null, sz: Int? = null, tmm: Int? = null,
                mv: Int? = null, mvj: Int? = null, role: Role? = null, ov: Int? = null,
                a: Int? = null, s: Int? = null) {
        val context = baseContext.copy()
        context.update(d, sz, tmm, mv, mvj, role, ov, a, s)
        unitDb.add(propertiesToUnit(family, generation, name, pv, context))
    }

    fun variant(pv: Int, d: UnitDslDamage? = null, sz: Int? = null, tmm: Int? = null,
                mv: Int? = null, mvj: Int? = null, role: Role? = null, ov: Int? = null,
                a: Int? = null, s: Int? = null) {
        variant("", pv, d, sz, tmm, mv, mvj, role, ov, a, s)
    }
}

class UnitDslFamilyContext(family: String, unitDb: UnitDb,
                           context: UnitDslContext = UnitDslContext()) :
        UnitDslGenerationContext(family, "", context, unitDb) {
    fun generation(name: String, builder: UnitDslGenerationContext.() -> kotlin.Unit) {
        val context = baseContext.copy()
        val generationContext = UnitDslGenerationContext(family, name, context, unitDb)
        generationContext.builder()
    }

    fun generation(builder: UnitDslGenerationContext.() -> kotlin.Unit) {
        generation("", builder)
    }

    fun generation(name: String, d: UnitDslDamage? = null, sz: Int? = null, tmm: Int? = null,
                   mv: Int? = null, mvj: Int? = null, role: Role? = null, ov: Int? = null,
                   a: Int? = null, s: Int? = null,
                   builder: UnitDslGenerationContext.() -> kotlin.Unit) {
        val context = baseContext.copy()
        context.update(d, sz, tmm, mv, mvj, role, ov, a, s)
        val generationContext = UnitDslGenerationContext(family, name, context, unitDb)
        generationContext.builder()
    }

    fun generation(d: UnitDslDamage? = null, sz: Int? = null, tmm: Int? = null, mv: Int? = null,
                   mvj: Int? = null, role: Role? = null, ov: Int? = null, a: Int? = null,
                   s: Int? = null, builder: UnitDslGenerationContext.() -> kotlin.Unit) {
        generation("", d, sz, tmm, mv, mvj, role, ov, a, s, builder)
    }
}

class UnitDslFamily(private val unitDb: UnitDb) {
    operator fun invoke(name: String, builder: UnitDslFamilyContext.() -> kotlin.Unit) {
        val context = UnitDslFamilyContext(name, unitDb)
        context.builder()
    }
}

/*
family("foo", role = brawler, sz = 2, tmm = 3, mv = 10) {
  generation("bar", ov = 1, a = 10, s = 5) {
    variant("baz", dS = 5, dM = 3, dL = min)
    variant("biff", dS = 6, dM = 1, dL = 0)
  }
}
family("foo", role = brawler, sz = 2, tmm = 3, mv = 10) {
  generation("bar", ov = 1, a = 10, s = 5) {
    variant("baz", d = D(s = 5, m = 3, l = min))
    variant("biff", d = D(s = 6, m = 1, l = 0))
  }
}
 */