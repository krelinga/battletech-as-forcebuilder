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
                    variant("Prime").role(missileBoat).damage(4, 4, 4).overheat(2).unit(42)
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

            family("Thor") {
                generation("Summoner").size(3).tmm(2).mv(10).mvj(10).armor(6).structure(4) {
                    variant("Prime").role(sniper).damage(4, 4, 4).unit(43)
                }
            }

            family("Loki") {
                generation("Hellbringer").size(3).tmm(2).mv(10).armor(4).structure(4) {
                    variant("Prime").role(striker).damage(4, 4, 2).overheat(3).unit(44)
                }
            }

            family("Shadow Cat") {
                generation("").size(2).tmm(3).mv(16).mvj(12).armor(4).structure(2) {
                    variant("Prime").role(striker).damage(3, 3, 2).unit(41)
                }
            }

            family("Uller") {
                generation("Kit Fox").size(1).tmm(2).mv(12).armor(3).structure(2) {
                    variant("Prime").role(sniper).damage(3, 3, 2).unit(26)
                }
            }

            family("Linebacker").size(3).tmm(2).mv(12).armor(6).structure(4) {
                variant("Prime").role(skirmisher).damage(4, 4, 4).overheat(1).unit(44)
            }

            family("Marauder") {
                generation("IIC").size(4).tmm(1).mv(8).armor(7).structure(7) {
                    variant("Standard").role(brawler).damage(6, 6, 5).overheat(3).unit(51)
                }
            }

            family("Black Lanner").size(2).tmm(3).mv(18).armor(5).structure(3) {
                variant("Prime").role(skirmisher).damage(3, 3, 2).overheat(1).unit(47)
            }

            family("Corvis").size(2).tmm(1).mv(8).mvj(8).armor(4).structure(3) {
                variant("Standard").role(brawler).damage(4, 4, 2).unit(29)
            }

            family("Rakshasa").size(3).tmm(2).mv(10).armor(7).structure(3) {
                variant("MDG-1A").role(skirmisher).damage(4, 4, 4).overheat(1).unit(46)
            }

            family("Sunder").size(4).tmm(1).mv(8).armor(9).structure(4) {
                variant("SD1-O").role(brawler).damage(6, 6, 0).overheat(1).unit(46)
            }

            family("Avatar").size(3).tmm(1).mv(8).armor(6).structure(3) {
                variant("AV1-0").role(brawler).damage(4, 5, 3).unit(38)
            }

            family("Blackjack").size(2).tmm(1).mv(8).mvj(8).armor(5).structure(2) {
                variant("BJ2-O").role(brawler).damage(5, 4, 2).unit(31)
            }

            family("Atlas").size(4).tmm(1).mv(6).armor(10).structure(8) {
                variant("AS7-D").role(juggernaut).damage(5, 5, 2).unit(52)
            }

            family("Grasshopper").size(3).tmm(1).mv(8).mvj(8).armor(7).structure(6) {
                variant("GHR-5H").role(skirmisher).damage(3, 4, min).unit(37)
            }

            family("Enforcer").size(2).tmm(1).mv(8).mvj(8).armor(5).structure(4) {
                variant("ENF-4R").role(skirmisher).damage(3, 2, 0).unit(25)
            }

            family("Trebuchet").size(2).tmm(2).mv(10).armor(4).structure(4) {
                variant("TBT-5N").role(missileBoat).damage(2, 2, 2).overheat(1).unit(29)
            }

            family("Catapult").size(3).tmm(1).mv(8).mvj(8).armor(6).structure(5) {
                variant("CPLT-A1").role(missileBoat).damage(1, 2, 2).unit(31)
            }

            family("Hunchback").size(2).tmm(1).mv(8).armor(5).structure(4) {
                variant("HBK-4G").role(juggernaut).damage(4, 3, 0).unit(28)
            }
        }
    }
}