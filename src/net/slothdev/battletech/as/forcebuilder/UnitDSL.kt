package net.slothdev.battletech.`as`.forcebuilder

interface UnitDslProperties {
    var mv: Int?
    var mvj: Int?
}

data class UnitDslContext(override var mv: Int? = null, override var mvj: Int? = null) :
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
    return Unit(nameParts.joinToString(" "), pv)
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
}

class UnitDslFamily(private val unitDb: UnitDb) {
    operator fun invoke(name: String, builder: UnitDslFamilyContext.() -> kotlin.Unit) {
        val context = UnitDslFamilyContext(name, unitDb)
        context.builder()
    }
}