package net.slothdev.battletech.`as`.forcebuilder

class MiniatureDslException(missingField: String) : RuntimeException("Missing field $missingField")

data class MiniatureDsl(private val db: MiniatureDb, private val unitDb: UnitDb,
                        private val theFamily: String? = null,
                        private val theGeneration: String? = null,
                        private val theVariant: String? = null, private var theColor: Color? = null,
                        private val theNote: String? = null) {
    // Constants that are useful to make the DSL code a little more terse.
    // Colors
    val brown = Color.BROWN
    val green = Color.GREEN
    val unpaintedGrayPlastic = Color.UNPAINTED_GRAY_PLASTIC
    val unpaintedPewter = Color.UNPAINTED_PEWTER
    val black = Color.BLACK
    val blueAndWhite = Color.BLUE_AND_WHITE

    fun family(x: String): MiniatureDsl {
        require(theFamily == null)
        return copy(theFamily = x)
    }

    fun family(x: String, ext: MiniatureDsl.() -> Unit) {
        family(x).ext()
    }

    fun generation(x: String): MiniatureDsl {
        require(theGeneration == null)
        return copy(theGeneration = x)
    }

    fun generation(x: String, ext: MiniatureDsl.() -> Unit) {
        generation(x).ext()
    }

    fun variant(x: String): MiniatureDsl {
        require(theVariant == null)
        return copy(theVariant = x)
    }

    fun variant(x: String, ext: MiniatureDsl.() -> Unit) {
        variant(x).ext()
    }

    fun color(x: Color): MiniatureDsl {
        require(theColor == null)
        return copy(theColor = x)
    }

    fun color(x: Color, ext: MiniatureDsl.() -> Unit) {
        color(x).ext()
    }

    fun note(x: String): MiniatureDsl {
        require(theNote == null)
        return copy(theNote = x)
    }

    fun note(x: String, ext: MiniatureDsl.() -> Unit) {
        note(x).ext()
    }

    fun miniature(id: String) {
        require(id.isNotBlank())
        val family = theFamily ?: throw MiniatureDslException("family")
        val generation = theGeneration ?: ""
        val variant = theVariant ?: ""
        val color = theColor ?: throw MiniatureDslException("color")

        val kindBuilder = StringBuilder()
        kindBuilder.append(family)
        if (generation != "") {
            kindBuilder.append(" ")
            kindBuilder.append(generation)
        }
        if (variant != "") {
            kindBuilder.append(" ")
            kindBuilder.append(variant)
        }
        val kind = kindBuilder.toString()

        db.add(Miniature(id, kind = kind, primaryColor = color,
                         supportedUnits = unitDb.withNamePrefix(kind)))
    }
}