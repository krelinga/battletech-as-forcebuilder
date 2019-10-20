package net.slothdev.battletech.`as`.forcebuilder

fun prettyPrintForces(forces: Forces) {
    fun prettyPrintOneSide(side: Set<Miniature>, sideNum: Int) {
        val sidePv = side.fold(0) { sum, current -> sum + current.supportedUnits.first().pv }
        val header = "Side $sideNum (${sidePv} points)"
        println(header)
        println("===================")
        for (mini in side) {
            val unit = mini.supportedUnits.first()
            println("* ${mini.kind} as ${unit.name} costing ${unit.pv} points")
        }
    }

    prettyPrintOneSide(forces.side1, 1)
    println()
    prettyPrintOneSide(forces.side2, 2)
}

fun main() {
    val builder = Builder(GlobalMiniatureDb.asSet())
    prettyPrintForces(builder.build(3, 120))
}