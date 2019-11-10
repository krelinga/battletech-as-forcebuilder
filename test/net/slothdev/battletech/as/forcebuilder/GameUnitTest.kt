package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class GameUnitTest {
    @Nested inner class Constructor {
        @Test fun `zero pv is OK`() {
            GameUnit("foo", 0)
        }
        @Test fun `negative pv causes error`() {
            assertFailsWith<IllegalArgumentException> { GameUnit("foo", -1) }
        }
        @Test fun `positive pv is OK`() {
            GameUnit("foo", 1)
        }
    }
}