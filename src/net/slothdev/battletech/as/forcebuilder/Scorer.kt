package net.slothdev.battletech.`as`.forcebuilder

import kotlin.math.abs
import kotlin.math.sqrt

data class Score(val finalScore: Double, val components: List<Pair<String, Double>>)

interface Scorer {
    operator fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score
}

class BasicScorer(val targetPv: Int, val unitsPerSide: Int) : Scorer {
    init {
        require(unitsPerSide > 0) { "requested too few units per-side: $unitsPerSide" }
        require(targetPv > 0) { "targetPvPerSide must be > 0, saw $targetPv" }

    }

    override fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score {
        val side1UnitsTerm = if (side1.size == unitsPerSide) 1.0 else 0.0
        val side2UnitsTerm = if (side2.size == unitsPerSide) 1.0 else 0.0

        val side1Points = side1.fold(0) { sum, element ->
            sum + element.supportedUnits.first().pointValue
        }
        val side2Points = side2.fold(0) { sum, element ->
            sum + element.supportedUnits.first().pointValue
        }

        fun pointDiff(x: Int, y: Int): Double {
            val diff = abs(x.toDouble() - y.toDouble())
            return 1.0 / (1.0 + diff)
        }

        // Terms between 0 and 1 that get smaller as the difference between sideNPoints and targetPv
        // increases.
        val side1TotalTerm = pointDiff(targetPv, side1Points)
        val side2TotalTerm = pointDiff(targetPv, side2Points)

        // Term between 0 and 1 that gets smaller as the difference between side1Points and
        // Side2Points increases.
        val pointDifferenceTerm = pointDiff(side1Points, side2Points)

        // Take all the terms into account.  Treat the per-side total terms as less important than
        // the difference between the two sides.
        val finalScore = side1UnitsTerm * side2UnitsTerm * sqrt(side1TotalTerm) * sqrt(
                side2TotalTerm) * pointDifferenceTerm
        fun pairOf(a: String, b: Double) = Pair<String, Double>(a, b)
        val components =
                listOf(pairOf("Target units ($unitsPerSide) vs side1 unit count (${side1.size})",
                              side1UnitsTerm),
                       pairOf("Target units ($unitsPerSide) vs side2 unit count (${side2.size})",
                              side2UnitsTerm),
                       pairOf("Target points ($targetPv) vs side1 points ($side1Points)",
                              sqrt(side1TotalTerm)),
                       pairOf("Target points ($targetPv) vs side2 points ($side1Points)",
                              sqrt(side2TotalTerm)),
                       pairOf("side1 points ($side1Points) vs side2 points ($side2Points)",
                              pointDifferenceTerm))
        return Score(finalScore, components)
    }
}