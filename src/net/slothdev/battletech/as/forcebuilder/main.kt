package net.slothdev.battletech.`as`.forcebuilder

fun main() {
    val minis = listOf<Miniature>(Miniature("foo"), Miniature("bar"))
    val builder = Builder(minis)
    println(builder.build(1))
}