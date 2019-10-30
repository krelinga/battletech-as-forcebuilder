package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class ForcesTest {
    val dummyScore = Score(1.0, listOf())

    @Nested
    inner class Constructor {
        @Test
        fun `at least one mini on each side is OK`() {
            Forces(dummyScore, setOf(Miniature(Color.GREEN, Unit("foo", 10))),
                   setOf(Miniature(Color.GREEN, Unit("bar", 10))))
        }
    }

    @Nested
    inner class Comparison {
        @Test
        fun `side1 and side2 are interchangable when compairing`() {
            val foo = Miniature(Color.GREEN, Unit("foo", 10))
            val bar = Miniature(Color.GREEN, Unit("bar", 20))
            val f1 = Forces(dummyScore, setOf(foo), setOf(bar))
            val f2 = Forces(dummyScore, setOf(bar), setOf(foo))
            assertTrue(f1 == f2)
        }
    }
}