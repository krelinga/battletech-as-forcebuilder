package net.slothdev.battletech.`as`.forcebuilder

data class Forces(val score: Score, private val miniatures: Set<Set<Miniature>>) {
    init {
        // Miniatures is a set of sets, where the entries in the outer sets comprise the two forces.
        // There's an edge case where the two forces are both empty, in which case there will be
        // only a single entry.
        require(miniatures.isNotEmpty()) { "miniatures must not be empty" }
        require(miniatures.size <= 2) { "too many entries in miniatures: ${miniatures.size}" }
    }

    // Allow specifying the sets for side1 and side2 gracefully.
    constructor(score: Score, side1: Set<Miniature>, side2: Set<Miniature>) : this(score,
                                                                                   setOf(side1,
                                                                                         side2))

    // Some convenience methods for dealing with the expected number of sides.
    val side1
        get() = miniatures.first()
    val side2
        get() = miniatures.last()
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
    private var scorer: Scorer? = null

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
        return Forces(scorer!!(side1, side2), side1, side2)
    }

    private fun reset() {
        miniState = minis.map { MiniState(it) }
    }

    fun build(unitsPerSide: Int, targetPvPerSide: Int): Forces {
        require(unitsPerSide > 0) { "requested too few units per-side: $unitsPerSide" }
        require(targetPvPerSide > 0) { "targetPvPerSide must be > 0, saw $targetPvPerSide" }
        require(minis.size >= 2 * unitsPerSide) {
            "not enough miniatures to allow $unitsPerSide units per-side"
        }


        this.scorer = BasicScorer(targetPvPerSide, unitsPerSide)
        reset()
        var bestSolution: Forces? = null
        do {
            val current = currentSolution()
            if (bestSolution?.score?.finalScore ?: 0.0 < current.score.finalScore) {
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