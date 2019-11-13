package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*

internal class MiniatureDslTest {
    val unitDb = UnitDb()

    init {
        unitDb.apply {
            add(GameUnit("Mad Cat Prime", 1))
            add(GameUnit("Vulture Prime", 1))
            add(GameUnit("Warhammer IIC", 1))
            add(GameUnit("Mad Cat A", 1))
        }
    }

    @Test
    fun `not nested`() {
        val db = MiniatureDb()
        MiniatureDsl(db, unitDb).apply {
            family("Mad Cat").variant("Prime").color(green).note("foo note").miniature("1")
            family("Vulture").variant("Prime").color(unpaintedGrayPlastic).miniature("2")
            family("Warhammer").generation("IIC").color(green).miniature("3")
        }
    }

    @Test
    fun nested() {
        val db = MiniatureDb()
        MiniatureDsl(db, unitDb).apply {
            family("Mad Cat") {
                variant("Prime").color(green) {
                    note("bar note").miniature("1")
                }
                variant("A") {
                    color(black).miniature("3")
                }
            }
        }
    }
}