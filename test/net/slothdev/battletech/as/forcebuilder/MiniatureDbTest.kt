package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class MiniatureDbTest {
    @Nested
    inner class Populate {
        @Test
        fun `minis with distinct ids are added`() {
            val db = MiniatureDb()
            val u = GameUnit("Foo", 10)
            val m1 = Miniature("1", "foo", Color.GREEN, setOf(u))
            val m2 = Miniature("2", "foo", Color.GREEN, setOf(u))
            db.add(m1)
            db.add(m2)
            assertEquals(setOf(m1, m2), db.asSet())
        }

        @Test
        fun `minis with duplicate ids are rejected`() {
            val db = MiniatureDb()
            val u = GameUnit("Foo", 10)
            db.add(Miniature("1", Color.GREEN, u))
            assertFailsWith<IllegalArgumentException> { db.add(Miniature("1", Color.GREEN, u)) }
        }
    }
}