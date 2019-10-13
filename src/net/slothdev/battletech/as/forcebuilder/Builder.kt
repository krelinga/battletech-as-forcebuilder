package net.slothdev.battletech.`as`.forcebuilder

import kotlin.Exception

data class Forces(val side1: List<Miniature>, val side2: List<Miniature>)

class BuilderException(message: String) : Exception(message)

class Builder(private val minis: List<Miniature>) {
    fun build(unitsPerSide: Int): Forces {
        if (unitsPerSide <= 0) {
            throw IllegalArgumentException("requested too few units per-side: $unitsPerSide")
        }
        if (minis.size < 2 * unitsPerSide) {
            throw BuilderException("not enough miniatures to allow $unitsPerSide units per-side")
        }
        val side1 = mutableListOf<Miniature>()
        val side2 = mutableListOf<Miniature>()
        for (i in 0 until unitsPerSide) {
            side1.add(minis[i])
        }
        for (i in unitsPerSide until unitsPerSide * 2) {
            side2.add(minis[i])
        }
        return Forces(side1, side2)
    }
}