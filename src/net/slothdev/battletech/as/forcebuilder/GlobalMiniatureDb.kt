package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        MiniatureDsl(this, GlobalUnitDb).apply {
            family("Mad Cat").generation("Timber Wolf") {
                color(black).note("Prize mech from gencon 2018").miniature(1)
                color(unpaintedGrayPlastic).miniature(2)
            }
            family("Vulture").generation("Mad Dog") {
                color(unpaintedPewter).miniature(3)
            }
            family("Black Hawk").generation("Nova") {
                color(brown).miniature(4)
                color(unpaintedGrayPlastic).miniature(5)
            }
            family("Ryoken").generation("Stormcrow") {
                color(green).miniature(6)
            }
        }
    }
}