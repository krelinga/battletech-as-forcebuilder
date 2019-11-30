package net.slothdev.battletech.`as`.forcebuilder

object GlobalMiniatureDb : MiniatureDb() {
    init {
        MiniatureDsl(this, GlobalUnitDb).apply {
            family("Thor").generation("Summoner") {
                color(unpaintedPewter).miniature("THR-1")
            }

            family("Loki").generation("Hellbringer") {
                color(unpaintedPewter).miniature("LOK-1")
            }

            family("Vulture").generation("Mad Dog") {
                color(unpaintedPewter).miniature("VLT-1")
            }

            family("Shadow Cat") {
                color(unpaintedPewter).miniature("SDC-1")
            }

            family("Uller").generation("Kit Fox") {
                color(unpaintedPewter).miniature("ULR-1")
            }

            family("Linebacker") {
                color(brown).miniature("LBR-1")
            }

            family("Marauder") {
                generation("IIC") {
                    color(brown).miniature("MAR-1")
                }
            }

            family("Black Hawk").generation("Nova") {
                color(brown).miniature("BHK-1")
            }

            family("Black Lanner") {
                color(brown).miniature("BLR-1")
            }

            family("Corvis") {
                color(brown).miniature("CVS-1")
            }

            family("Rakshasa") {
                color(blueAndWhite).miniature("RKS-1")
            }

            family("Sunder") {
                color(blueAndWhite).miniature("SND-1")
            }

            family("Avatar") {
                color(blueAndWhite).miniature("AVT-1")
            }

            family("Blackjack") {
                color(blueAndWhite).miniature("BJK-1")
            }

            family("Atlas") {
                color(shinyBlue).miniature("ATS-1")
            }

            family("Grasshopper") {
                color(shinyBlue).miniature("GHR-1")
            }

            family("Enforcer") {
                color(shinyBlue).miniature("ENF-1")
            }

            family("Trebuchet") {
                color(shinyBlue).miniature("TBT-1")
            }

            family("Catapult") {
                color(packers).miniature("CPT-1")
            }
        }
    }
}