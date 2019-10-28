package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class MiniatureTest {
    @Nested inner class Constructor {
        @Test fun `positive pv is OK`() {
            Miniature(Color.GREEN, Unit("foo", 1))
        }
        @Test fun `zero pv is OK`() {
            Miniature(Color.GREEN, Unit("foo", 0))
        }
        @Test fun `negative pv throws exception`() {
            assertFailsWith<IllegalArgumentException> { Miniature(Color.GREEN, Unit("foo", -1)) }
        }
    }
}