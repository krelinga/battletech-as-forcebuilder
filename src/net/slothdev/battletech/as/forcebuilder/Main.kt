package net.slothdev.battletech.`as`.forcebuilder

fun main() {
    val minis = setOf<Miniature>(Miniature(Unit("Mad Cat Prime", 10)),
                                 Miniature(Unit("Vulture Prime", 20)),
                                 Miniature(Unit("Thor Prime", 30)),
                                 Miniature(Unit("Loki Prime", 40)))
    val builder = Builder(minis)
    println(builder.build(2, 1))
}