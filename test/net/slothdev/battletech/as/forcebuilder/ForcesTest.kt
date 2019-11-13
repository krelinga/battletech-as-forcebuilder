package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class ForcesTest {
    val dummyScore = Score(1.0, listOf())

    @Nested
    inner class Constructor {
        @Test
        fun `at least one mini on each side is OK`() {
            Forces(dummyScore, setOf(Miniature("1", Color.GREEN, GameUnit("foo", 10))),
                   setOf(Miniature("1", Color.GREEN, GameUnit("bar", 10))))
        }
    }

    @Nested
    inner class Comparison {
        @Test
        fun `side1 and side2 are interchangable when compairing`() {
            val foo = Miniature("1", Color.GREEN, GameUnit("foo", 10))
            val bar = Miniature("2", Color.GREEN, GameUnit("bar", 20))
            val f1 = Forces(dummyScore, setOf(foo), setOf(bar))
            val f2 = Forces(dummyScore, setOf(bar), setOf(foo))
            assertTrue(f1 == f2)
        }
    }
}