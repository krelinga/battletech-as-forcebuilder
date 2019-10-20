package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        add(Miniature("GenCon 2018 Mad Cat", GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature("Plastic Mad Cat", GlobalUnitDb.withNamePrefix("Mad Cat")))
        add(Miniature("unpainted metal Vulture", GlobalUnitDb.withNamePrefix("Vulture")))
    }
}