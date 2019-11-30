package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class GlobalMiniatureDbTest {
    @Test
    fun `has Thor mini`() {
        assertTrue(GlobalMiniatureDb.asSet().any { it.kind.contains("Thor") })
    }
}