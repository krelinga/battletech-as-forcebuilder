package net.slothdev.battletech.`as`.forcebuilder

object GlobalUnitDb : UnitDb() {
    init {
        // Mad Cats
        add(Unit("Mad Cat Prime", 54))
        add(Unit("Mad Cat A", 59))
        add(Unit("Mad Cat B", 48))
        add(Unit("Mad Cat C", 50))
        add(Unit("Mad Cat D", 51))
        add(Unit("Mad Cat E", 53))
        add(Unit("Mad Cat F", 54))
        add(Unit("Mad Cat H", 59))

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
    }
}