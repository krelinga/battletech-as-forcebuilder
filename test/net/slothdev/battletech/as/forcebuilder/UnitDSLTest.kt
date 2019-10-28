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
                dS = D(5)
                dM = D(5)
                dL = D(4)
            }
        }
        assertEquals(unitDb.asSet(),
                     setOf(Unit("f foo", 10, size = 3, targetMovementModifier = 2, movement = 10,
                                role = Role.BRAWLER, armor = 8, structure = 4, overheat = 0,
                                movementJumping = 8, damageShort = Damage(5),
                                damageMedium = Damage(5), damageLong = Damage(4))))
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
                    dS = D(5)
                    dM = D(5)
                    dL = D(4)
                }
            }
        }
        assertEquals(unitDb.asSet(),
                     setOf(Unit("f bar foo", 20, size = 3, targetMovementModifier = 2,
                                movement = 10, role = Role.BRAWLER, overheat = 0, armor = 8,
                                structure = 4, movementJumping = 8, damageShort = Damage(5),
                                damageMedium = Damage(5), damageLong = Damage(4))))
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
                dS = D(5)
                dM = D(5)
                dL = D(4)

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
        val unitBase = Unit("", 1, size = 3, targetMovementModifier = 2, movement = 10,
                            role = Role.BRAWLER, overheat = 0, armor = 8, structure = 4,
                            damageShort = Damage(5), damageMedium = Damage(5),
                            damageLong = Damage(4))
        assertEquals(unitDb.asSet(),
                     setOf(unitBase.copy(name = "f bar foo", pointValue = 30, movementJumping = 8),
                           unitBase.copy("f bar foo2", 40, movementJumping = 9),
                           unitBase.copy("f bar " + "foo3", 50, movementJumping = 10),
                           unitBase.copy("f bar", 60, movementJumping = 11)))
    }
}