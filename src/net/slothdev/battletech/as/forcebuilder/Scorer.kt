package net.slothdev.battletech.`as`.forcebuilder

data class OldScore(val finalScore: Double, val components: List<Pair<String, Double>>)

// Interfaces for general scoring.
interface Score {
    // The actual numerical score.
    val score: Double

    // An explanation of how the numerical score was arrived at.
    fun explain(): String
}

interface Scorer {
    operator fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): OldScore
}