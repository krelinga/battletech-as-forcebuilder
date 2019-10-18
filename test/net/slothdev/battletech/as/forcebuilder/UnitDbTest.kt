package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class UnitDbTest {
    @Nested
    inner class Populate {
        @Test
        fun `units with distinct names are added`() {
            val db = UnitDb()
            val u1 = Unit("foo", 10)
            val u2 = Unit("bar", 10)
            db.add(u1)
            db.add(u2)
            assertEquals(setOf(u1, u2), db.asSet())
        }

        @Test
        fun `units with duplicate names are rejected`() {
            val db = UnitDb()
            db.add(Unit("foo", 10))
            assertFailsWith<IllegalArgumentException> { db.add(Unit("foo", 20)) }
        }
    }
}