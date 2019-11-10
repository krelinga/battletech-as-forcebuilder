package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class GlobalMiniatureDbTest {
    @Test
    fun `has Mad Cat mini`() {
        assertTrue(GlobalMiniatureDb.asSet().any { it.kind.contains("Mad Cat") })
    }
}