package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class BuilderTest {
    @Nested inner class Build {
        // TODO: add more tests now that I've added some logic around checking all options.  Test that optimal solutions are found.
        @Test fun `just enough miniatures works`() {
            val minis = setOf(
                Miniature("foo"),
                Miniature("bar"),
                Miniature("baz"),
                Miniature("biff")
            )
            val b = Builder(minis)
            val forces = b.build(2, 1)
            assertEquals(2, forces.side1.size)
            assertEquals(2, forces.side2.size)
        }
        @Test fun `finds optimal solution`() {
            val foo = Miniature("foo", 50)
            val bar = Miniature("bar", 50)
            val baz = Miniature("baz", 51)
            val biff = Miniature("biff", 49)
            val targetPv = 100
            val expected = Forces(targetPv, setOf(foo, bar), setOf(baz, biff))
            val builder = Builder(setOf(foo, bar, baz, biff))
            val actual = builder.build(2, targetPv)
            assertEquals(actual, expected)
        }

        @Test
        fun `supports multiple units per-miniature`() {
            val m1u1 = Unit("M1-U1", 50)
            val m1 = Miniature("M1", setOf(m1u1, Unit("M1-U2", 75)))
            val m2u1 = Unit("M2-U1", 50)
            val m2 = Miniature("M2", setOf(m2u1, Unit("M2-U2", 75)))
            val targetPv = 50
            val expected = Forces(targetPv, setOf(Miniature("M1", setOf(m1u1))),
                                  setOf(Miniature("M2", setOf(m2u1))))
            val builder = Builder(setOf(m1, m2))
            val actual = builder.build(1, targetPv)
            assertEquals(expected, actual)
        }
        @Test fun `not enough miniatures throws exception`() {
            val b = Builder(setOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(1, 1) }
        }
        @Test fun `zero miniatures per-side throws exception`() {
            val b = Builder(setOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(0, 1) }
        }
        @Test fun `negative miniatures per-side throws exception`() {
            val b = Builder(setOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(-1, 1) }
        }
        @Test fun `zero target pv per-side throws exception`() {
            val b = Builder(setOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(1, 0) }
        }
        @Test fun `negative target pv per-side throws exception`() {
            val b = Builder(setOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(1, -1) }
        }
    }
}