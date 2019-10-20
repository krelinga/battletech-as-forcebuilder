package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.Test
import kotlin.test.*

internal class UnitDSLTest {
    @Test
    fun `variant in family`() {
        val unitDb = UnitDb()
        val family = UnitDslFamily(unitDb)
        family("f") {
            mv = 10

            variant("foo", 10) {
                mvj = 8
            }
        }
        assertEquals(unitDb.asSet(), setOf(Unit("f foo", 10)))
    }

    @Test
    fun `variant in generation in family`() {
        val unitDb = UnitDb()
        val family = UnitDslFamily(unitDb)
        family("f") {
            mv = 10

            generation("bar") {
                variant("foo", 20) {
                    mvj = 8
                }
            }
        }
        assertEquals(unitDb.asSet(), setOf(Unit("f bar foo", 20)))
    }

    @Test
    fun `multiple variants in generation`() {
        val unitDb = UnitDb()
        val family = UnitDslFamily(unitDb)
        family("f") {
            mv = 10

            generation("bar") {
                variant("foo", 30) {
                    mvj = 8
                }
                variant("foo2", 40) {
                    mvj = 8
                }
                variant("foo3", 50) {
                    mvj = 8
                }
                variant(60) {
                    mvj = 6
                }
            }
        }
        assertEquals(unitDb.asSet(),
                     setOf(Unit("f bar foo", 30), Unit("f bar foo2", 40), Unit("f bar foo3", 50),
                           Unit("f bar", 60)))
    }
}