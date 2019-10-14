package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class ForcesTest {
    @Nested
    inner class Constructor {
        @Test
        fun `targetPv greater than 0 is OK`() {
            Forces(1, listOf(), listOf())
        }

        @Test
        fun `targetPv lte 0 fails`() {
            assertFailsWith<IllegalArgumentException> { Forces(0, listOf(), listOf()) }
        }
    }

    @Nested
    inner class Score {
        @Test
        fun `difference between side1 point value and target matters`() {
            val f = Forces(1, listOf(Miniature("foo", 0)), listOf(Miniature("bar", 1)))
            assert(f.score < 1.0)
        }

        @Test
        fun `difference between side2 point value and target matters`() {
            val f = Forces(1, listOf(Miniature("foo", 1)), listOf(Miniature("bar", 0)))
            assert(f.score < 1.0)
        }

        @Test
        fun `no differences gives a score of 1`() {
            val f = Forces(10, listOf(Miniature("foo", 10)), listOf(Miniature("bar", 10)))
            assert(f.score == 1.0)
        }

        @Test
        fun `difference between point values of the sides matters more than between side and total`() {
            val bigDiffBetweenSides =
                    Forces(10, listOf(Miniature("foo", 14)), listOf(Miniature("bar", 6)))
            val smallDiffBetweenSides =
                    Forces(10, listOf(Miniature("foo", 6)), listOf(Miniature("bar", 6)))
            assert(smallDiffBetweenSides.score > bigDiffBetweenSides.score) {
                "smallDiffBetweenSides: ${smallDiffBetweenSides.score} vs bigDiffBetweenSides: ${bigDiffBetweenSides.score}"
            }
        }
    }
}