package net.slothdev.battletech.`as`.forcebuilder

import kotlin.math.abs
import kotlin.math.sqrt

data class Score(val finalScore: Double, val components: List<Pair<String, Double>>)

interface Scorer {
    operator fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score
}

class PvAndDistanceScorer(val targetPv: Int) : Scorer {
    override fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): Score {
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

        // Terms between 0 and 1 that get smaller as the difference between sideNPoints and targetPv increases.
        val side1TotalTerm = pointDiff(targetPv, side1Points)
        val side2TotalTerm = pointDiff(targetPv, side2Points)

        // Term between 0 and 1 that gets smaller as the difference between side1Points and Side2Points increases.
        val pointDifferenceTerm = pointDiff(side1Points, side2Points)

        // Take all the terms into account.  Treat the per-side total terms as less important than the difference
        // between the two sides.
        val finalScore = sqrt(side1TotalTerm) * sqrt(side2TotalTerm) * pointDifferenceTerm
        fun pairOf(a: String, b: Double) = Pair<String, Double>(a, b)
        val components = listOf(pairOf("Target points ($targetPv) vs side1 points ($side1Points)",
                                       sqrt(side1TotalTerm)),
                                pairOf("Target points ($targetPv) vs side2 points ($side1Points)",
                                       sqrt(side2TotalTerm)),
                                pairOf("side1 points ($side1Points) vs side2 points ($side2Points)",
                                       pointDifferenceTerm))
        return Score(finalScore, components)
    }
}

data class Forces(val targetPv: Int, private val miniatures: Set<Set<Miniature>>) {
    init {
        require(targetPv > 0) { "targetPv must be > 0, saw $targetPv"}
        // Miniatures is a set of sets, where the entries in the outer sets comprise the two forces.
        // There's an edge case where the two forces are both empty, in which case there will be
        // only a single entry.
        require(miniatures.isNotEmpty()) { "miniatures must not be empty" }
        require(miniatures.size <= 2) { "too many entries in miniatures: ${miniatures.size}" }
    }

    // Allow specifying the sets for side1 and side2 gracefully.
    constructor(targetPv: Int, side1: Set<Miniature>, side2: Set<Miniature>) : this(
            targetPv, setOf(side1, side2))

    // Some convenience methods for dealing with the expected number of sides.
    val side1
        get() = miniatures.first()
    val side2
        get() = miniatures.last()

    val score: Double
        get() {
            val scorer = PvAndDistanceScorer(targetPv)
            return scorer(side1, side2).finalScore
        }
}

class BuilderException(message: String) : Exception(message)

// TODO: there's an optimization opportunity here right now.  I decided that side1 and side2 are
//  interchangeable, but the way that we generate possible forces doesn't account for that right
//  now, so we end up making the same force but with side1 and side2 swapped.  That means we're
//  doing roughly 2x the work that is needed.  This implementation should still give correct
//  results, it will just be inefficient.
class Builder private constructor(private val minis: List<Miniature>) {
    // We want to enforce that the given set of minis is unique, but internally it makes more sense
    // to store them as a list.
    constructor(minis: Set<Miniature>) : this(minis.toList())

    private enum class MiniSide {
        NONE, SIDE1, SIDE2
    }

    private inner class MiniState(private val mini: Miniature) {
        // the current state of the miniature.
        var side: MiniSide = MiniSide.NONE
            private set

        init {
            require(mini.supportedUnits.isNotEmpty())
        }

        private var unitIterator = mini.supportedUnits.iterator()
        private var currentUnit = unitIterator.next()

        val current: Miniature
            get() = Miniature(mini.kind, mini.primaryColor, setOf(currentUnit))

        // true if further calls to next() will not change the state anymore.
        val done
            get() = side == MiniSide.SIDE2 && !unitIterator.hasNext()

        fun next() {
            if (done) return
            when (side) {
                MiniSide.NONE -> side = MiniSide.SIDE1
                else -> {
                    if (unitIterator.hasNext()) {
                        currentUnit = unitIterator.next()
                    } else {
                        assert(side == MiniSide.SIDE1) {
                            "MiniState::done should be true, so we " + "shouldn't ever get here."
                        }
                        side = MiniSide.SIDE2
                        unitIterator = mini.supportedUnits.iterator()
                    }
                }
            }
        }

        fun reset() {
            side = MiniSide.NONE
        }
    }

    private var miniState = listOf<MiniState>()
    private var targetPv = 0
    private var unitsPerSide = 0

    private fun nextMiniState(): Boolean {
        val done = miniState.fold(true) { sum, entry -> sum && entry.done }
        if (done) return false
        for (entry in miniState) {
            if (entry.done) {
                entry.reset()
            } else {
                entry.next()
                break
            }
        }
        return true
    }

    private fun currentSolution() : Forces {
        val side1 = mutableSetOf<Miniature>()
        val side2 = mutableSetOf<Miniature>()

        for (entry in miniState) {
            when (entry.side) {
                MiniSide.SIDE1 -> side1.add(entry.current)
                MiniSide.SIDE2 -> side2.add(entry.current)
                MiniSide.NONE -> {
                }
            }
        }
        return Forces(targetPv, side1, side2)
    }

    private fun reset(unitsPerSide: Int, targetPvPerSide: Int) {
        this.unitsPerSide = unitsPerSide
        targetPv = targetPvPerSide
        miniState = minis.map { MiniState(it) }
    }

    private fun solutionIsOK(forces: Forces): Boolean {
        return forces.side1.size == unitsPerSide && forces.side2.size == unitsPerSide
    }

    fun build(unitsPerSide: Int, targetPvPerSide: Int): Forces {
        require(unitsPerSide > 0) { "requested too few units per-side: $unitsPerSide" }
        require(targetPvPerSide > 0) { "targetPvPerSide must be > 0, saw $targetPvPerSide" }
        require(minis.size >= 2 * unitsPerSide) { "not enough miniatures to allow $unitsPerSide units per-side" }

        reset(unitsPerSide, targetPvPerSide)
        var bestSolution: Forces? = null
        do {
            val current = currentSolution()
            if (!solutionIsOK(current)) continue
            if (bestSolution?.score ?: 0.0 < current.score) {
                bestSolution = current
            }
        } while (nextMiniState())

        if (bestSolution != null) {
            return bestSolution
        } else {
            throw BuilderException("no solutions found!")
        }
    }
}