package net.slothdev.battletech.`as`.forcebuilder

import kotlin.math.abs
import kotlin.math.sqrt

// Legacy ... to be refactored.
data class OldScore(val finalScore: Double, val components: List<Pair<String, Double>>)

interface OldScorer {
    operator fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): OldScore
}

// Interfaces for general scoring.
interface Score {
    // The actual numerical score.
    val score: Double

    // An explanation of how the numerical score was arrived at.
    fun explain(): String
}

interface Scorer {
    operator fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score
}

// Utility class for tracking scores that have names in generic scoring functions.
data class NamedScore(val name: String, val score: Score)

// Multiply N scores.
class MultiplyScore(private val inputs: List<NamedScore>) : Score {
    override val score: Double
        get() = inputs.fold(1.0) { acc, namedScore -> acc * namedScore.score.score }

    override fun explain(): String {
        return ""
    }
}

// Take the square root of a score.
class SquareRootScore(private val input: Score) : Score {
    override val score: Double
        get() = sqrt(input.score)

    override fun explain(): String {
        return ""
    }
}

// PV difference vs. some target.
class PvDifferenceScore(private val pv1: Int, private val pv2: Int) : Score {
    override val score: Double
        get() = 1.0 / (1.0 + abs(pv1 - pv2))

    override fun explain(): String {
        return ""
    }
}

class PvDifferenceScorer(private val targetPv: Int) : Scorer {
    override fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score {
        //return PvDifferenceScore(1, 2)
        fun pointTotal(side: Set<Miniature>): Int {
            return side.fold(0) { acc, mini -> acc + mini.supportedUnits.first().pointValue }
        }

        val side1Pv = pointTotal(side1)
        val side2Pv = pointTotal(side2)
        return MultiplyScore(listOf(NamedScore("side1 vs target", SquareRootScore(
                PvDifferenceScore(side1Pv, targetPv))), NamedScore("side2 vs target",
                                                                   SquareRootScore(
                                                                           PvDifferenceScore(
                                                                                   side2Pv,
                                                                                   targetPv))),
                                    NamedScore("side1 vs side2",
                                               PvDifferenceScore(side1Pv, side2Pv))))
    }
}

class UnitCountDifferenceScore(private val count1: Int, private val count2: Int) : Score {
    override val score: Double
        get() = if (count1 == count2) 1.0 else 0.0

    override fun explain(): String {
        return ""
    }

}

// Unit count vs. some target.
class UnitCountDifferenceScorer(private val targetCount: Int) : Scorer {
    override fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score {
        return MultiplyScore(listOf(NamedScore("side1 unit count",
                                               UnitCountDifferenceScore(side1.size, targetCount)),
                                    NamedScore("side2 unit count",
                                               UnitCountDifferenceScore(side2.size, targetCount))))
    }
}

class ScorerV0(private val targetPv: Int, private val targetUnitCount: Int)