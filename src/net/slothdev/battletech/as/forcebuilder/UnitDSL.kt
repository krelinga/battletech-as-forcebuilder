package net.slothdev.battletech.`as`.forcebuilder

interface UnitDslProperties {
    var sz: Int?
    var tmm: Int?
    var mv: Int?
    var mvj: Int?
    var role: Role?
    var dS: Int?
    var dM: Int?
    var dL: Int?
    var ov: Int?
    var a: Int?
    var s: Int?
}

data class UnitDslContext(override var sz: Int? = null, override var tmm: Int? = null,
                          override var mv: Int? = null, override var mvj: Int? = null,
                          override var role: Role? = null, override var dS: Int? = null,
                          override var dM: Int? = null, override var dL: Int? = null,
                          override var ov: Int? = null, override var a: Int? = null,
                          override var s: Int? = null) :
        UnitDslProperties

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
    return Unit(nameParts.joinToString(" "), pv, sz = properties.sz!!, tmm = properties.tmm!!,
                mv = properties.mv!!, mvj = properties.mvj!!, role = properties.role!!,
                dS = properties.dS, dM = properties.dM, dL = properties.dL, ov = properties.ov!!,
                a = properties.a!!, s = properties.s!!)
}

open class UnitDslGenerationContext(protected val family: String, protected val generation: String,
                                    protected val baseContext: UnitDslContext,
                                    protected val unitDb: UnitDb) :
        UnitDslProperties by baseContext {
    fun variant(name: String, pv: Int, builder: UnitDslContext.() -> kotlin.Unit) {
        val context = baseContext.copy()
        context.builder()

        unitDb.add(propertiesToUnit(family, generation, name, pv, context))
    }

    fun variant(pv: Int, builder: UnitDslContext.() -> kotlin.Unit) {
        variant("", pv, builder)
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
}

class UnitDslFamily(private val unitDb: UnitDb) {
    operator fun invoke(name: String, builder: UnitDslFamilyContext.() -> kotlin.Unit) {
        val context = UnitDslFamilyContext(name, unitDb)
        context.builder()
    }
}