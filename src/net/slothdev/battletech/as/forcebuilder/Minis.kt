package net.slothdev.battletech.`as`.forcebuilder

data class Miniature(val kind: String, val pv: Int = 0) {
    init {
        require(pv >= 0) { "pv parameter must be >= 0, we saw $pv" }
    }
}