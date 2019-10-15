package net.slothdev.battletech.`as`.forcebuilder

fun main() {
    val minis = setOf<Miniature>(
        Miniature("foo"), Miniature("bar"), Miniature("biff"), Miniature("bonk")
    )
    val builder = Builder(minis)
    println(builder.build(2, 1))
}