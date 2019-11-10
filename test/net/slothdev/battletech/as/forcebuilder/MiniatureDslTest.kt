package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*

internal class MiniatureDslTest {
    val unitDb = UnitDb()

    init {
        unitDb.apply {
            add(Unit("Mad Cat Prime", 1))
            add(Unit("Vulture Prime", 1))
            add(Unit("Warhammer IIC", 1))
            add(Unit("Mad Cat A", 1))
        }
    }

    @Test
    fun `not nested`() {
        val db = MiniatureDb()
        MiniatureDsl(db, unitDb).apply {
            family("Mad Cat").variant("Prime").color(green).miniature(0)
            family("Vulture").variant("Prime").color(unpaintedGrayPlastic).miniature(1)
            family("Warhammer").generation("IIC").color(green).miniature(2)
        }
    }

    @Test
    fun nested() {
        val db = MiniatureDb()
        MiniatureDsl(db, unitDb).apply {
            family("Mad Cat") {
                variant("Prime").color(green) {
                    miniature(1)
                }
                variant("A") {
                    color(black).miniature(3)
                }
            }
        }
    }
}