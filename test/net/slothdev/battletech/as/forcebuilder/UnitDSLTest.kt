package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.Test
import kotlin.test.*

internal class UnitDSLTest {
    @Test
    fun `variant in family`() {
        val unitDb = UnitDb()
        val family = UnitDslFamily(unitDb)
        family("f") {
            sz = 3
            tmm = 2
            mv = 10
            role = Role.BRAWLER
            a = 8
            s = 4
            ov = 0

            variant("foo", 10) {
                mvj = 8
                dS = 5
                dM = 5
                dL = 4
            }
        }
        assertEquals(unitDb.asSet(),
                     setOf(Unit("f foo", 10, sz = 3, tmm = 2, mv = 10, role = Role.BRAWLER, a = 8,
                                s = 4, ov = 0, mvj = 8, dS = 5, dM = 5, dL = 4)))
    }

    @Test
    fun `variant in generation in family`() {
        val unitDb = UnitDb()
        val family = UnitDslFamily(unitDb)
        family("f") {
            sz = 3
            tmm = 2
            mv = 10
            role = Role.BRAWLER
            ov = 0

            generation("bar") {
                a = 8
                s = 4

                variant("foo", 20) {
                    mvj = 8
                    dS = 5
                    dM = 5
                    dL = 4
                }
            }
        }
        assertEquals(unitDb.asSet(),
                     setOf(Unit("f bar foo", 20, sz = 3, tmm = 2, mv = 10, role = Role.BRAWLER,
                                ov = 0, a = 8, s = 4, mvj = 8, dS = 5, dM = 5, dL = 4)))
    }

    @Test
    fun `multiple variants in generation`() {
        val unitDb = UnitDb()
        val family = UnitDslFamily(unitDb)
        family("f") {
            sz = 3
            tmm = 2
            mv = 10
            role = Role.BRAWLER
            ov = 0

            generation("bar") {
                a = 8
                s = 4
                dS = 5
                dM = 5
                dL = 4

                variant("foo", 30) {
                    mvj = 8
                }
                variant("foo2", 40) {
                    mvj = 9
                }
                variant("foo3", 50) {
                    mvj = 10
                }
                variant(60) {
                    mvj = 11
                }
            }
        }
        val unitBase =
                Unit("", 1, sz = 3, tmm = 2, mv = 10, role = Role.BRAWLER, ov = 0, a = 8, s = 4,
                     dS = 5, dM = 5, dL = 4)
        assertEquals(unitDb.asSet(), setOf(unitBase.copy(name = "f bar foo", pv = 30, mvj = 8),
                                           unitBase.copy("f bar foo2", 40, mvj = 9),
                                           unitBase.copy("f bar " + "foo3", 50, mvj = 10),
                                           unitBase.copy("f bar", 60, mvj = 11)))
    }
}