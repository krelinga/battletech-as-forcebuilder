package net.slothdev.battletech.`as`.forcebuilder

object GlobalUnitDb : UnitDb() {
    init {
        UnitDsl(this).apply {
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

                    variant("Bounty Hunter").role(brawler).damage(6, 6, 3).unit(59)
                    variant("Bounty Hunter 2").mvj(6).role(brawler).damage(6, 6, 2).overheat(2)
                        .unit(55)

                    variant("Pryde").mvj(8).role(missileBoat).damage(4, 4, 4).overheat(2).unit(51)
                }
            }

            family("Vulture") {
                generation("Mad Dog").size(3).tmm(2).mv(10).armor(5).structure(3) {
                    variant("prime").role(missileBoat).damage(4, 4, 4).overheat(2).unit(42)
                    variant("A").role(skirmisher).damage(5, 5, 2).overheat(2).unit(42)
                    variant("B").role(skirmisher).damage(4, 4, 2).overheat(4).unit(41)
                    variant("C").role(sniper).damage(3, 3, 3).unit(33)
                    variant("D").role(skirmisher).damage(8, 6, 2).overheat(2).unit(48)
                    variant("E").role(sniper).damage(3, 3, 3).unit(33)
                    variant("F").role(skirmisher).damage(6, 5, 3).overheat(1).unit(43)
                    variant("G").role(brawler).damage(4, 4, 3).overheat(3).unit(39)
                    variant("H").role(missileBoat).damage(6, 6, 3).overheat(1).unit(48)
                }
            }

            family("Black Hawk") {
                generation("Nova").size(2).tmm(2).mv(10).mvj(10).armor(5).structure(3) {
                    variant("Prime").role(skirmisher).damage(5, 5, 0).overheat(4).unit(41)
                    variant("A").role(sniper).damage(3, 3, 3).overheat(1).unit(35)
                    variant("B").role(sniper).damage(3, 3, 2).unit(32)
                    variant("C").role(sniper).damage(2, 3, 2).unit(31)
                    variant("D").role(missileBoat).damage(2, 2, 2).unit(29)
                    variant("E").role(skirmisher).damage(6, 5, 1).unit(40)
                    variant("F").role(skirmisher).damage(4, 4, 2).unit(36)
                    variant("H").role(skirmisher).damage(6, 6, 0).overheat(4).unit(45)
                    variant("I").role(skirmisher).damage(5, 5, 2).overheat(2).unit(42)
                    variant("S").role(skirmisher).damage(6, 5, 0).unit(43)
                }
            }

            family("Ryoken") {
                generation("Stormcrow").size(2).tmm(2).mv(12).armor(6).structure(3) {
                    variant("Prime").role(striker).damage(5, 5, 2).unit(43)
                    variant("A").role(striker).damage(5, 5, 2).overheat(2).unit(45)
                    variant("B").role(striker).damage(4, 4, 0).overheat(3).unit(39)
                    variant("C").role(striker).damage(4, 4, 2).unit(39)
                    variant("D").role(missileBoat).damage(3, 3, 3).unit(39)
                    variant("E").role(striker).damage(7, 6, 2).overheat(1).unit(52)
                    variant("F").role(striker).damage(3, 3, 0).overheat(2).unit(37)
                    variant("G").role(striker).damage(5, 5, 2).unit(43)
                    variant("H").role(striker).damage(5, 5, 0).overheat(1).unit(42)
                    variant("I").role(sniper).damage(3, 3, 3).overheat(2).unit(42)
                }
            }
        }
    }
}