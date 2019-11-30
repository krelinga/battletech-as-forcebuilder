package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        MiniatureDsl(this, GlobalUnitDb).apply {
            family("Thor").generation("Summoner") {
                color(unpaintedPewter).miniature("THR-1")
            }
        }
    }
}