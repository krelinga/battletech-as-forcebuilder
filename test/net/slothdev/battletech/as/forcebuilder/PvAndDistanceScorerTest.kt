package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class PvAndDistanceScorerTest {
    @Test
    fun `difference between side1 point value and target matters`() {
        val scorer = PvAndDistanceScorer(1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 0))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 1))))
        assertTrue(score.finalScore < 1.0)
    }

    @Test
    fun `difference between side2 point value and target matters`() {
        val scorer = PvAndDistanceScorer(1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 1))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 0))))
        assertTrue(score.finalScore < 1.0)
    }

    @Test
    fun `no differences gives a score of 1`() {
        val scorer = PvAndDistanceScorer(10)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 10))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 10))))
        assertTrue(score.finalScore == 1.0)
    }

    @Test
    fun `difference between point values of the sides matters more than between side and total`() {
        val scorer = PvAndDistanceScorer(10)
        val bigDiffBetweenSides = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 14))),
                                         setOf(Miniature(Color.GREEN, Unit("bar", 6))))
        val smallDiffBetweenSides = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 6))),
                                           setOf(Miniature(Color.GREEN, Unit("bar", 6))))
        assertTrue(smallDiffBetweenSides.finalScore > bigDiffBetweenSides.finalScore,
                   "smallDiffBetweenSides: ${smallDiffBetweenSides.finalScore} vs bigDiffBetweenSides: ${bigDiffBetweenSides.finalScore}")
    }

}