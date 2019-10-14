package net.slothdev.battletech.`as`.forcebuilder

import kotlin.math.abs
import kotlin.math.sqrt

data class Forces(val side1: List<Miniature>, val side2: List<Miniature>)

class Builder(private val minis: List<Miniature>) {
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
        return !miniState.fold(true) { sum, current -> sum && current == MiniState.NONE }
    }

    private fun currentSolution() : Forces {
        val side1 = mutableListOf<Miniature>()
        val side2 = mutableListOf<Miniature>()

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