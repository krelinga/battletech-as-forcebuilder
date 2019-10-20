package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        add(Miniature("GenCon 2018 Mad Cat", GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature("Plastic Mad Cat", GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature("unpainted metal Vulture", GlobalUnitDb.withNamePrefix("Vulture")))
        add(Miniature("Black Hawk from ebay", GlobalUnitDb.withNamePrefix("Black Hawk")))
        add(Miniature("Black Hawk plastic", GlobalUnitDb.withNamePrefix("Black Hawk")))
    }
}