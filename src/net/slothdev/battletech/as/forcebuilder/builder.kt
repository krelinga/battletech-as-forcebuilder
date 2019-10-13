package net.slothdev.battletech.`as`.forcebuilder

data class Forces(val side1: List<Miniature>, val side2: List<Miniature>)

class Builder(val minis: List<Miniature>) {
    fun build(unitsPerSide: Int): Forces {
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