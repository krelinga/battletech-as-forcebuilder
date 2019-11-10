package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        add(Miniature(1, "GenCon 2018 Mad Cat", Color.BLACK,
                      GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature(2, "Plastic Mad Cat", Color.UNPAINTED_GRAY_PLASTIC,
                      GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature(3, "unpainted metal Vulture", Color.UNPAINTED_PEWTER,
                      GlobalUnitDb.withNamePrefix("Vulture")))
        add(Miniature(4, "Black Hawk from ebay", Color.BROWN,
                      GlobalUnitDb.withNamePrefix("Black " + "Hawk")))
        add(Miniature(5, "Black Hawk plastic", Color.UNPAINTED_PEWTER,
                      GlobalUnitDb.withNamePrefix("Black " + "Hawk")))
        add(Miniature(6, "Ryoken with basic green paint", Color.GREEN,
                      GlobalUnitDb.withNamePrefix("Ryoken")))
    }
}