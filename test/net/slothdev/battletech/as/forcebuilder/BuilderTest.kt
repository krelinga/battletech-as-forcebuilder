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