package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class BuilderTest {
    @Nested inner class Build {
        @Test fun `just enough miniatures works`() {
            val minis = listOf(
                Miniature("foo"),
                Miniature("bar"),
                Miniature("baz"),
                Miniature("biff")
            )
            val b = Builder(minis)
            val forces = b.build(2)
            assertEquals(2, forces.side1.size)
            assertEquals(2, forces.side2.size)
        }
        @Test fun `not enough miniatures throws exception`() {
            val b = Builder(listOf(Miniature("foo")))
            assertFailsWith<BuilderException> { b.build(1) }
        }
    }
}