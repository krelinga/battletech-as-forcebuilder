package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class BuilderTest {
    @Nested inner class Build {
        // TODO: add more tests now that I've added some logic around checking all options.  Test that optimal solutions are found.
        @Test fun `just enough miniatures works`() {
            val minis = listOf(
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
        @Test fun `not enough miniatures throws exception`() {
            val b = Builder(listOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(1, 1) }
        }
        @Test fun `zero miniatures per-side throws exception`() {
            val b = Builder(listOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(0, 1) }
        }
        @Test fun `negative miniatures per-side throws exception`() {
            val b = Builder(listOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(-1, 1) }
        }
        @Test fun `zero target pv per-side throws exception`() {
            val b = Builder(listOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(1, 0) }
        }
        @Test fun `negative target pv per-side throws exception`() {
            val b = Builder(listOf(Miniature("foo")))
            assertFailsWith<IllegalArgumentException> { b.build(1, -1) }
        }
    }
}