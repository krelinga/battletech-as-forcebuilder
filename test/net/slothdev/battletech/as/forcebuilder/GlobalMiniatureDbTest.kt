package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class GlobalMiniatureDbTest {
    @Test
    fun `has GenCon 2018 mini`() {
        assertTrue(GlobalMiniatureDb.asSet().any { it.kind.contains("GenCon 2018") })
    }
}