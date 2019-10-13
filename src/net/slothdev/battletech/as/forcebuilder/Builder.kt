package net.slothdev.battletech.`as`.forcebuilder

data class Forces(val side1: List<Miniature>, val side2: List<Miniature>)

class Builder(private val minis: List<Miniature>) {
    fun build(unitsPerSide: Int, targetPvPerSide: Int): Forces {
        require(unitsPerSide > 0) { "requested too few units per-side: $unitsPerSide" }
        require(targetPvPerSide > 0) { "targetPvPerSide must be > 0, saw $targetPvPerSide" }
        require(minis.size >= 2 * unitsPerSide) { "not enough miniatures to allow $unitsPerSide units per-side" }
        val side1 = mutableListOf<Miniature>()
        val side2 = mutableListOf<Miniature>()
        for (i in 0 until unitsPerSide) {
            side1.add(minis[i])
        }
        for (i in unitsPerSide until unitsPerSide * 2) {
            side2.add(minis[i])
        }
        return Forces(side1, side2)
    }
}