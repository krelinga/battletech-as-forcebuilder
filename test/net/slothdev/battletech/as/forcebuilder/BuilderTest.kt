package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class BuilderTest {
    @Nested inner class Build {
        // TODO: add more tests now that I've added some logic around checking all options.  Test that optimal solutions are found.
        @Test fun `just enough miniatures works`() {
            val minis = setOf(Miniature(Color.GREEN, Unit("foo", 10)),
                              Miniature(Color.GREEN, Unit("bar", 10)),
                              Miniature(Color.GREEN, Unit("baz", 10)),
                              Miniature(Color.GREEN, Unit("biff", 10))
                             )
            val b = Builder(minis)
            val forces = b.build(2, 1)
            assertEquals(2, forces.side1.size)
            assertEquals(2, forces.side2.size)
        }
        @Test fun `finds optimal solution`() {
            val foo = Miniature(Color.GREEN, Unit("foo", 50))
            val bar = Miniature(Color.GREEN, Unit("bar", 50))
            val baz = Miniature(Color.GREEN, Unit("baz", 51))
            val biff = Miniature(Color.GREEN, Unit("biff", 49))
            val targetPv = 100
            val expected = Forces(Score(1.0, listOf()), setOf(foo, bar), setOf(baz, biff))
            val builder = Builder(setOf(foo, bar, baz, biff))
            val actual = builder.build(2, targetPv)
            assertEquals(expected, actual.copy(score = Score(1.0, listOf())))
        }

        @Test
        fun `supports multiple units per-miniature`() {
            val m1u1 = Unit("M1-U1", 50)
            val m1 = Miniature("M1", Color.GREEN, setOf(m1u1, Unit("M1-U2", 75)))
            val m2u1 = Unit("M2-U1", 50)
            val m2 = Miniature("M2", Color.GREEN, setOf(m2u1, Unit("M2-U2", 75)))
            val targetPv = 50
            val expected = Forces(Score(1.0, listOf()),
                                  setOf(Miniature("M1", Color.GREEN, setOf(m1u1))),
                                  setOf(Miniature("M2", Color.GREEN, setOf(m2u1))))
            val builder = Builder(setOf(m1, m2))
            val actual = builder.build(1, targetPv)
            assertEquals(expected, actual.copy(score = Score(1.0, listOf())))
        }
        @Test
        fun `supports multiple units per-miniature last unit can be chosen`() {
            val m1u1 = Unit("M1-U1", 50)
            val m1 = Miniature("M1", Color.GREEN, setOf(Unit("M1-U2", 75), m1u1))
            val m2u1 = Unit("M2-U1", 50)
            val m2 = Miniature("M2", Color.GREEN, setOf(Unit("M2-U2", 75), m2u1))
            val targetPv = 50
            val dummyScore = Score(1.0, listOf())
            val expected = Forces(dummyScore, setOf(Miniature("M1", Color.GREEN, setOf(m1u1))),
                                  setOf(Miniature("M2", Color.GREEN, setOf(m2u1))))
            val builder = Builder(setOf(m1, m2))
            val actual = builder.build(1, targetPv)
            assertEquals(expected, actual.copy(score = dummyScore))
        }
        @Test fun `not enough miniatures throws exception`() {
            val b = Builder(setOf(Miniature(Color.GREEN, Unit("foo", 10))))
            assertFailsWith<BuilderException> { b.build(1, 1) }
        }
        @Test fun `zero miniatures per-side throws exception`() {
            val b = Builder(setOf(Miniature(Color.GREEN, Unit("foo", 10))))
            assertFailsWith<IllegalArgumentException> { b.build(0, 1) }
        }
        @Test fun `negative miniatures per-side throws exception`() {
            val b = Builder(setOf(Miniature(Color.GREEN, Unit("foo", 10))))
            assertFailsWith<IllegalArgumentException> { b.build(-1, 1) }
        }
        @Test fun `zero target pv per-side throws exception`() {
            val b = Builder(setOf(Miniature(Color.GREEN, Unit("foo", 10))))
            assertFailsWith<IllegalArgumentException> { b.build(1, 0) }
        }
        @Test fun `negative target pv per-side throws exception`() {
            val b = Builder(setOf(Miniature(Color.GREEN, Unit("foo", 10))))
            assertFailsWith<IllegalArgumentException> { b.build(1, -1) }
        }
    }
}