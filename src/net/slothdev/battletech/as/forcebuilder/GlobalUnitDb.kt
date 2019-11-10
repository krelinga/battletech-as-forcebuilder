package net.slothdev.battletech.`as`.forcebuilder

object GlobalUnitDb : UnitDb() {
    private val family = UnitDslFamily(this)

    init {
        family("Mad Cat") {
            generation() {
                sz = 3
                tmm = 2
                mv = 10
                // TODO: add roles per-variant (because they're different)
                role = Role.BRAWLER
                // TODO: make mvj optional?
                mvj = 0
                a = 8
                s = 4
                variant("Prime", 54) {
                    dS = D(5)
                    dM = D(5)
                    dL = D(4)
                    ov = 1
                }
                variant("A", 59) {
                    dS = D(7)
                    dM = D(7)
                    dL = D(3)
                    ov = 1
                }
                variant("B", 48) {
                    dS = D(4)
                    dM = D(4)
                    dL = D(4)
                    ov = 0
                }
                variant("C", 50) {
                    dS = D(4)
                    dM = D(4)
                    dL = D(4)
                    ov = 1
                }
                variant("D", 51) {
                    dS = D(5)
                    dM = D(5)
                    dL = D(3)
                    ov = 1
                }
                variant("E", 53) {
                    dS = D(7)
                    dM = D(5)
                    dL = D(4)
                    ov = 0
                }
                variant("F", 54) {
                    dS = D(5)
                    dM = D(5)
                    dL = D(4)
                    ov = 2
                }
                variant("H", 59) {
                    dS = D(6)
                    dM = D(6)
                    dL = D(4)
                    ov = 1
                }
            }
        }

        // Vultures
        add(GameUnit("Vulture Prime", 42))
        add(GameUnit("Vulture A", 42))
        add(GameUnit("Vulture B", 41))
        add(GameUnit("Vulture C", 33))
        add(GameUnit("Vulture D", 48))
        add(GameUnit("Vulture E", 33))
        add(GameUnit("Vulture F", 43))
        add(GameUnit("Vulture G", 39))
        add(GameUnit("Vulture H", 48))

        //  Black Hawks
        add(GameUnit("Black Hawk Prime", 41))
        add(GameUnit("Black Hawk A", 35))
        add(GameUnit("Black Hawk B", 32))
        add(GameUnit("Black Hawk C", 31))
        add(GameUnit("Black Hawk D", 29))
        add(GameUnit("Black Hawk E", 40))
        add(GameUnit("Black Hawk F", 36))
        add(GameUnit("Black Hawk H", 45))
        add(GameUnit("Black Hawk I", 42))

        // Ryokens
        add(GameUnit("Ryoken Prime", 43))
        add(GameUnit("Ryoken A", 45))
        add(GameUnit("Ryoken B", 39))
        add(GameUnit("Ryoken C", 39))
        add(GameUnit("Ryoken D", 39))
        add(GameUnit("Ryoken E", 52))
        add(GameUnit("Ryoken F", 37))
        add(GameUnit("Ryoken G", 43))
        add(GameUnit("Ryoken H", 42))
        add(GameUnit("Ryoken I", 42))
    }
}