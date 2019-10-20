package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class GlobalUnitDbTest {
    @Test
    fun `has Mad Cats`() {
        assertTrue(GlobalUnitDb.asSet().any { it.name.contains("Mad Cat") })
    }
}