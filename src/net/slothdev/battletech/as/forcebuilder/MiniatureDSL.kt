package net.slothdev.battletech.`as`.forcebuilder

class MiniatureDslException(missingField: String) : RuntimeException("Missing field $missingField")

data class MiniatureDsl(private val db: MiniatureDb, private val unitDb: UnitDb,
                        private var theFamily: String? = null,
                        private var theGeneration: String? = null,
                        private var theVariant: String? = null,
                        private var theColor: Color? = null) {
    // Constants that are useful to make the DSL code a little more terse.
    // Colors
    val brown = Color.BROWN
    val green = Color.GREEN
    val unpaintedGrayPlastic = Color.UNPAINTED_GRAY_PLASTIC
    val unpaintedPewter = Color.UNPAINTED_PEWTER
    val black = Color.BLACK

    fun family(x: String): MiniatureDsl {
        require(theFamily == null)
        return copy(theFamily = x)
    }

    fun family(x: String, ext: MiniatureDsl.() -> kotlin.Unit) {
        family(x).ext()
    }

    fun generation(x: String): MiniatureDsl {
        require(theGeneration == null)
        return copy(theGeneration = x)
    }

    fun generation(x: String, ext: MiniatureDsl.() -> kotlin.Unit) {
        generation(x).ext()
    }

    fun variant(x: String): MiniatureDsl {
        require(theVariant == null)
        return copy(theVariant = x)
    }

    fun variant(x: String, ext: MiniatureDsl.() -> kotlin.Unit) {
        variant(x).ext()
    }

    fun color(x: Color): MiniatureDsl {
        require(theColor == null)
        return copy(theColor = x)
    }

    fun color(x: Color, ext: MiniatureDsl.() -> kotlin.Unit) {
        color(x).ext()
    }

    fun miniature(id: Int) {
        require(id > 0)  // TODO(krelinga0: this is just there to avoid a warning.
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

        db.add(Miniature(kind = kind, primaryColor = color,
                         supportedUnits = unitDb.withNamePrefix(kind)))
    }
}

/*
group(unitNamePrefix = "Mad Cat") {
    mini(name = "Gencon 2018 Prize Mad Cat", id = 10, color: green)
}
group(color = brown) {
    group(unitNamePrefix = "Vulture") {
        mini(name = "Vulture", id = 12)
    }
}
 */

/*
group {
  family = "Warhammer"
  group {
    generation = "IIC"
    mini {
      variant = ""
      color = green
      id = 10
    }
  }
}
 */

/*
db {
    family("Warhammer") {
        generation("IIC") {
            color(green).id(10)
        }
    }
    family("Mad Cat").color(brown).id(11)
}
 */