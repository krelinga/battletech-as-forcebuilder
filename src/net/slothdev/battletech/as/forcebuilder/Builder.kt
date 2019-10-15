package net.slothdev.battletech.`as`.forcebuilder

import kotlin.math.abs
import kotlin.math.sqrt

data class Forces(val targetPv: Int, private val miniatures: Set<Set<Miniature>>) {
    init {
        require(targetPv > 0) { "targetPv must be > 0, saw $targetPv"}
        require(miniatures.size == 2) { "expected 2 entries in miniatures, saw ${miniatures.size}" }
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
            val side1Points = side1.fold(0) { sum, element -> sum + element.pv }
            val side2Points = side2.fold(0) { sum, element -> sum + element.pv }

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
            return sqrt(side1TotalTerm) * sqrt(side2TotalTerm) * pointDifferenceTerm
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

    private enum class MiniState {
        NONE, SIDE1, SIDE2
    }

    private var miniState = mutableListOf<MiniState>()
    private var targetPv = 0
    private var unitsPerSide = 0

    private fun nextMiniState(): Boolean {
        miniStateLoop@ for (i in 0 until miniState.size) {
            when(miniState[i]) {
                MiniState.NONE -> {
                    miniState[i] = MiniState.SIDE1
                    break@miniStateLoop
                }
                MiniState.SIDE1 -> {
                    miniState[i] = MiniState.SIDE2
                    break@miniStateLoop
                }
                MiniState.SIDE2 -> {
                    miniState[i] = MiniState.NONE
                    // Don't break out here because we need to find some non-overflow index to
                    // change.
                }
            }
        }
        // If we've totally wrapped around then we're done.
        val done = miniState.fold(true) { sum, current -> sum && current == MiniState.NONE }
        return !done
    }

    private fun currentSolution() : Forces {
        val side1 = mutableSetOf<Miniature>()
        val side2 = mutableSetOf<Miniature>()

        for (i in 0 until miniState.size) {
            when(miniState[i]) {
                MiniState.SIDE1 -> side1.add(minis[i])
                MiniState.SIDE2 -> side2.add(minis[i])
                else -> {
                    // nothing to do in this case, just skip it.
                }
            }
        }
        return Forces(targetPv, side1, side2)
    }

    private fun reset(unitsPerSide: Int, targetPvPerSide: Int) {
        this.unitsPerSide = unitsPerSide
        targetPv = targetPvPerSide
        miniState = MutableList<MiniState>(minis.size) { MiniState.NONE }

        // The first state produces a solution where no minis are assigned to any side.  This is
        // a bit of a problem for the Forces class, which has some implementation details that
        // assume the two sides are never equal (which having two empty sides violates).  The
        // easy solution here is to just skip over that state, since we'd never care about it in
        // practice anyway.
        nextMiniState()
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