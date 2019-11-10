package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class MiniatureDbTest {
    @Nested
    inner class Populate {
        @Test
        fun `minis with distinct kinds are added`() {
            val db = MiniatureDb()
            val u = GameUnit("Foo", 10)
            val m1 = Miniature("foo-x", Color.GREEN, setOf(u))
            val m2 = Miniature("foo-y", Color.GREEN, setOf(u))
            db.add(m1)
            db.add(m2)
            assertEquals(setOf(m1, m2), db.asSet())
        }

        @Test
        fun `minis with duplicate names are rejected`() {
            val db = MiniatureDb()
            val u = GameUnit("Foo", 10)
            db.add(Miniature(Color.GREEN, u))
            assertFailsWith<IllegalArgumentException> { db.add(Miniature(Color.GREEN, u)) }
        }
    }
}