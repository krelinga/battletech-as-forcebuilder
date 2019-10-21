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
                    dS = 5
                    dM = 5
                    dL = 4
                    ov = 1
                }
                variant("A", 59) {
                    dS = 7
                    dM = 7
                    dL = 3
                    ov = 1
                }
                variant("B", 48) {
                    dS = 4
                    dM = 4
                    dL = 4
                    ov = 0
                }
                variant("C", 50) {
                    dS = 4
                    dM = 4
                    dL = 4
                    ov = 1
                }
                variant("D", 51) {
                    dS = 5
                    dM = 5
                    dL = 3
                    ov = 1
                }
                variant("E", 53) {
                    dS = 7
                    dM = 5
                    dL = 4
                    ov = 0
                }
                variant("F", 54) {
                    dS = 5
                    dM = 5
                    dL = 4
                    ov = 2
                }
                variant("H", 59) {
                    dS = 6
                    dM = 6
                    dL = 4
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