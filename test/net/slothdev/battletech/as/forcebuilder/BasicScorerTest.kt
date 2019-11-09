package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class BasicScorerTest {
    @Test
    fun `difference between side1 point value and target matters`() {
        val scorer = BasicScorer(1, 1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 0))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 1))))
        assertTrue(score.finalScore < 1.0)
    }

    @Test
    fun `difference between side2 point value and target matters`() {
        val scorer = BasicScorer(1, 1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 1))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 0))))
        assertTrue(score.finalScore < 1.0)
    }

    @Test
    fun `no differences gives a score of 1`() {
        val scorer = BasicScorer(10, 1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 10))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 10))))
        assertTrue(score.finalScore == 1.0)
    }

    @Test
    fun `difference between point values of the sides matters more than between side and total`() {
        val scorer = BasicScorer(10, 1)
        val bigDiffBetweenSides = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 14))),
                                         setOf(Miniature(Color.GREEN, Unit("bar", 6))))
        val smallDiffBetweenSides = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 6))),
                                           setOf(Miniature(Color.GREEN, Unit("bar", 6))))
        assertTrue(smallDiffBetweenSides.finalScore > bigDiffBetweenSides.finalScore,
                   "smallDiffBetweenSides: ${smallDiffBetweenSides.finalScore} vs bigDiffBetweenSides: ${bigDiffBetweenSides.finalScore}")
    }

    @Test
    fun `side1 zero units yields zero score`() {
        val scorer = BasicScorer(10, 1)
        val score = scorer(setOf(), setOf(Miniature(Color.GREEN, Unit("foo", 14))))
        assertEquals(0.0, score.finalScore)
    }

    @Test
    fun `side2 zero units yields zero score`() {
        val scorer = BasicScorer(10, 1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo", 14))), setOf())
        assertEquals(0.0, score.finalScore)
    }

    @Test
    fun `side1 too few units yields zero score`() {
        val scorer = BasicScorer(10, 2)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo1", 14))),
                           setOf(Miniature(Color.GREEN, Unit("bar1", 6)),
                                 Miniature(Color.GREEN, Unit("bar2", 6))))
        assertEquals(0.0, score.finalScore)
    }

    @Test
    fun `side2 too few units yields zero score`() {
        val scorer = BasicScorer(10, 2)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo1", 14)),
                                 Miniature(Color.GREEN, Unit("foo2", 14))),
                           setOf(Miniature(Color.GREEN, Unit("bar1", 6))))
        assertEquals(0.0, score.finalScore)
    }

    @Test
    fun `side1 too many units yields zero score`() {
        val scorer = BasicScorer(10, 1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo1", 14)),
                                 Miniature(Color.GREEN, Unit("foo2", 14))),
                           setOf(Miniature(Color.GREEN, Unit("bar1", 6))))
        assertEquals(0.0, score.finalScore)
    }

    @Test
    fun `side2 too many units yields zero score`() {
        val scorer = BasicScorer(10, 1)
        val score = scorer(setOf(Miniature(Color.GREEN, Unit("foo1", 14))),
                           setOf(Miniature(Color.GREEN, Unit("bar1", 6)),
                                 Miniature(Color.GREEN, Unit("bar2", 6))))
        assertEquals(0.0, score.finalScore)
    }


    @Test
    fun `zero miniatures per-side throws exception`() {
        assertFailsWith<IllegalArgumentException> { BasicScorer(1, 0) }
    }


    @Test
    fun `zero target pv per-side throws exception`() {
        assertFailsWith<IllegalArgumentException> { BasicScorer(0, 1) }
    }


    @Test
    fun `negative miniatures per-side throws exception`() {
        assertFailsWith<IllegalArgumentException> { BasicScorer(1, -1) }
    }

    @Test
    fun `negative target pv per-side throws exception`() {
        assertFailsWith<IllegalArgumentException> { BasicScorer(-1, 1) }
    }
}