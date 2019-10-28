package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class ForcesTest {
    @Nested
    inner class Constructor {
        @Test
        fun `targetPv greater than 0 and at least one mini on each side is OK`() {
            Forces(1, setOf(Miniature(Color.GREEN, Unit("foo", 10))),
                   setOf(Miniature(Color.GREEN, Unit("bar", 10))))
        }

        @Test
        fun `targetPv lte 0 fails`() {
            assertFailsWith<IllegalArgumentException> {
                Forces(0, setOf(Miniature(Color.GREEN, Unit("foo", 10))),
                       setOf(Miniature(Color.GREEN, Unit("bar", 10))))
            }
        }
    }

    @Nested
    inner class Score {
        @Test
        fun `difference between side1 point value and target matters`() {
            val f = Forces(1, setOf(Miniature(Color.GREEN, Unit("foo", 0))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 1))))
            assertTrue(f.score < 1.0)
        }

        @Test
        fun `difference between side2 point value and target matters`() {
            val f = Forces(1, setOf(Miniature(Color.GREEN, Unit("foo", 1))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 0))))
            assertTrue(f.score < 1.0)
        }

        @Test
        fun `no differences gives a score of 1`() {
            val f = Forces(10, setOf(Miniature(Color.GREEN, Unit("foo", 10))),
                           setOf(Miniature(Color.GREEN, Unit("bar", 10))))
            assertTrue(f.score == 1.0)
        }

        @Test
        fun `difference between point values of the sides matters more than between side and total`() {
            val bigDiffBetweenSides = Forces(10, setOf(Miniature(Color.GREEN, Unit("foo", 14))),
                                             setOf(Miniature(Color.GREEN, Unit("bar", 6))))
            val smallDiffBetweenSides = Forces(10, setOf(Miniature(Color.GREEN, Unit("foo", 6))),
                                               setOf(Miniature(Color.GREEN, Unit("bar", 6))))
            assertTrue(smallDiffBetweenSides.score > bigDiffBetweenSides.score,
                       "smallDiffBetweenSides: ${smallDiffBetweenSides.score} vs bigDiffBetweenSides: ${bigDiffBetweenSides.score}")
        }
    }

    @Nested
    inner class Comparison {
        @Test
        fun `side1 and side2 are interchangable when compairing`() {
            val foo = Miniature(Color.GREEN, Unit("foo", 10))
            val bar = Miniature(Color.GREEN, Unit("bar", 20))
            val f1 = Forces(10, setOf(foo), setOf(bar))
            val f2 = Forces(10, setOf(bar), setOf(foo))
            assertTrue(f1 == f2)
        }
    }
}