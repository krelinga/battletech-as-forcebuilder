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
        add(Unit("Vulture Prime", 42))
        add(Unit("Vulture A", 42))
        add(Unit("Vulture B", 41))
        add(Unit("Vulture C", 33))
        add(Unit("Vulture D", 48))
        add(Unit("Vulture E", 33))
        add(Unit("Vulture F", 43))
        add(Unit("Vulture G", 39))
        add(Unit("Vulture H", 48))

        //  Black Hawks
        add(Unit("Black Hawk Prime", 41))
        add(Unit("Black Hawk A", 35))
        add(Unit("Black Hawk B", 32))
        add(Unit("Black Hawk C", 31))
        add(Unit("Black Hawk D", 29))
        add(Unit("Black Hawk E", 40))
        add(Unit("Black Hawk F", 36))
        add(Unit("Black Hawk H", 45))
        add(Unit("Black Hawk I", 42))

        // Ryokens
        add(Unit("Ryoken Prime", 43))
        add(Unit("Ryoken A", 45))
        add(Unit("Ryoken B", 39))
        add(Unit("Ryoken C", 39))
        add(Unit("Ryoken D", 39))
        add(Unit("Ryoken E", 52))
        add(Unit("Ryoken F", 37))
        add(Unit("Ryoken G", 43))
        add(Unit("Ryoken H", 42))
        add(Unit("Ryoken I", 42))
    }
}