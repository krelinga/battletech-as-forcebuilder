package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        add(Miniature("GenCon 2018 Mad Cat", Color.BLACK, GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature("Plastic Mad Cat", Color.UNPAINTED_GRAY_PLASTIC,
                      GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature("unpainted metal Vulture", Color.UNPAINTED_PEWTER,
                      GlobalUnitDb.withNamePrefix("Vulture")))
        add(Miniature("Black Hawk from ebay", Color.BROWN,
                      GlobalUnitDb.withNamePrefix("Black " + "Hawk")))
        add(Miniature("Black Hawk plastic", Color.UNPAINTED_PEWTER,
                      GlobalUnitDb.withNamePrefix("Black " + "Hawk")))
        add(Miniature("Ryoken with basic green paint", Color.GREEN,
                      GlobalUnitDb.withNamePrefix("Ryoken")))
    }
}