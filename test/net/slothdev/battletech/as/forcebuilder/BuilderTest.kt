package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class BuilderTest {
    @Nested
    inner class Build {
        // TODO: add more tests now that I've added some logic around checking all options.  Test that optimal solutions are found.
        @Test
        fun `just enough miniatures works`() {
            val minis = setOf(Miniature(1, Color.GREEN, GameUnit("foo", 10)),
                              Miniature(2, Color.GREEN, GameUnit("bar", 10)),
                              Miniature(3, Color.GREEN, GameUnit("baz", 10)),
                              Miniature(4, Color.GREEN, GameUnit("biff", 10)))
            val b = Builder(minis, BasicScorer(1, 2))
            val forces = b.build()
            assertEquals(2, forces.side1.size)
            assertEquals(2, forces.side2.size)
        }

        @Test
        fun `finds optimal solution`() {
            val foo = Miniature(1, Color.GREEN, GameUnit("foo", 50))
            val bar = Miniature(2, Color.GREEN, GameUnit("bar", 50))
            val baz = Miniature(3, Color.GREEN, GameUnit("baz", 51))
            val biff = Miniature(4, Color.GREEN, GameUnit("biff", 49))
            val targetPv = 100
            val expected = Forces(Score(1.0, listOf()), setOf(foo, bar), setOf(baz, biff))
            val builder = Builder(setOf(foo, bar, baz, biff), BasicScorer(targetPv, 2))
            val actual = builder.build()
            assertEquals(expected, actual.copy(score = Score(1.0, listOf())))
        }

        @Test
        fun `supports multiple units per-miniature`() {
            val m1u1 = GameUnit("M1-U1", 50)
            val m1 = Miniature(1, "M1", Color.GREEN, setOf(m1u1, GameUnit("M1-U2", 75)))
            val m2u1 = GameUnit("M2-U1", 50)
            val m2 = Miniature(2, "M2", Color.GREEN, setOf(m2u1, GameUnit("M2-U2", 75)))
            val targetPv = 50
            val expected = Forces(Score(1.0, listOf()),
                                  setOf(Miniature(1, "M1", Color.GREEN, setOf(m1u1))),
                                  setOf(Miniature(2, "M2", Color.GREEN, setOf(m2u1))))
            val builder = Builder(setOf(m1, m2), BasicScorer(targetPv, 1))
            val actual = builder.build()
            assertEquals(expected, actual.copy(score = Score(1.0, listOf())))
        }

        @Test
        fun `supports multiple units per-miniature last unit can be chosen`() {
            val m1u1 = GameUnit("M1-U1", 50)
            val m1 = Miniature(1, "M1", Color.GREEN, setOf(GameUnit("M1-U2", 75), m1u1))
            val m2u1 = GameUnit("M2-U1", 50)
            val m2 = Miniature(2, "M2", Color.GREEN, setOf(GameUnit("M2-U2", 75), m2u1))
            val targetPv = 50
            val dummyScore = Score(1.0, listOf())
            val expected = Forces(dummyScore, setOf(Miniature(1, "M1", Color.GREEN, setOf(m1u1))),
                                  setOf(Miniature(2, "M2", Color.GREEN, setOf(m2u1))))
            val builder = Builder(setOf(m1, m2), BasicScorer(targetPv, 1))
            val actual = builder.build()
            assertEquals(expected, actual.copy(score = dummyScore))
        }

        @Test
        fun `not enough miniatures throws exception`() {
            val b = Builder(setOf(Miniature(1, Color.GREEN, GameUnit("foo", 10))),
                            BasicScorer(1, 1))
            assertFailsWith<BuilderException> { b.build() }
        }
    }
}