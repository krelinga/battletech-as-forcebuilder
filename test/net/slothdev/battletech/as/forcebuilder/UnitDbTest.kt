package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class UnitDbTest {
    @Nested
    inner class Populate {
        @Test
        fun `units with distinct names are added`() {
            val db = UnitDb()
            val u1 = GameUnit("foo", 10)
            val u2 = GameUnit("bar", 10)
            db.add(u1)
            db.add(u2)
            assertEquals(setOf(u1, u2), db.asSet())
        }

        @Test
        fun `units with duplicate names are rejected`() {
            val db = UnitDb()
            db.add(GameUnit("foo", 10))
            assertFailsWith<IllegalArgumentException> { db.add(GameUnit("foo", 20)) }
        }
    }

    @Nested
    inner class WithNamePrefix {
        @Test
        fun `needle at beginning is found`() {
            val unitDb = UnitDb()
            unitDb.add(GameUnit("foobar", 1))
            assertTrue(unitDb.withNamePrefix("foo").isNotEmpty())
        }

        @Test
        fun `needle in middle is not found`() {
            val unitDb = UnitDb()
            unitDb.add(GameUnit("barfoo", 1))
            assertTrue(unitDb.withNamePrefix("foo").isEmpty())
        }

        @Test
        fun `needle which is not present is not found`() {
            val unitDb = UnitDb()
            unitDb.add(GameUnit("barbar", 1))
            assertTrue(unitDb.withNamePrefix("foo").isEmpty())
        }
    }
}