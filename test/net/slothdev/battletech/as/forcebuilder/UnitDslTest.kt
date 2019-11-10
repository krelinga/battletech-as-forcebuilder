package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.Test
import kotlin.test.*

internal class UnitDslTest {
    @Test
    fun nested() {
        val unitDb = UnitDb()
        UnitDsl(unitDb).apply {
            family("Mad Cat") {
                generation("Timber Wolf").size(3).tmm(2).mv(10).armor(8).structure(4) {
                    variant("Prime").role(brawler).damage(5, 5, 4).overheat(1).unit(54)
                    variant("A").role(skirmisher).damage(7, 7, 3).overheat(1).unit(59)
                    role(sniper).damage(4, 4, 4) {
                        variant("B").unit(48)
                        variant("C").overheat(1).unit(50)
                    }
                    variant("D").role(skirmisher).damage(5, 5, 3).overheat(1).unit(51)
                    variant("E").role(sniper).damage(7, 5, 4).unit(53)
                    variant("F").role(missileBoat).damage(5, 5, 4).overheat(2).unit(54)
                    variant("H").role(missileBoat).damage(6, 6, 4).overheat(1).unit(59)
                }
            }
        }
    }
}