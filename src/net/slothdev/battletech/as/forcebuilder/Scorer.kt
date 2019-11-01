package net.slothdev.battletech.`as`.forcebuilder

data class OldScore(val finalScore: Double, val components: List<Pair<String, Double>>)

interface Scorer {
    operator fun invoke(side1: Set<Miniature>, side2: Set<Miniature>): OldScore
}
