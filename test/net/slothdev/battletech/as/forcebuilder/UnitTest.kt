package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class UnitTest {
    @Nested inner class Constructor {
        @Test fun `zero pv is OK`() {
            Unit("foo", 0)
        }
        @Test fun `negative pv causes error`() {
            assertFailsWith<IllegalArgumentException> { Unit("foo", -1) }
        }
        @Test fun `positive pv is OK`() {
            Unit("foo", 1)
        }
    }
}