package net.slothdev.battletech.`as`.forcebuilder

import org.junit.jupiter.api.*
import kotlin.test.*

internal class DamageTest {
    @Test
    fun `zero damage is OK`() {
        val d = Damage(0)
        assertEquals(0, d.value)
        assertEquals(false, d.minimal)
    }

    @Test
    fun `positive damage is OK`() {
        val d = Damage(1)
        assertEquals(1, d.value)
        assertEquals(false, d.minimal)
    }

    @Test
    fun `negative damage throws error`() {
        assertFailsWith<IllegalArgumentException> { Damage(-1) }
    }

    @Test
    fun `minimal damage is OK`() {
        val d = Damage.minimal()
        assertEquals(0, d.value)
        assertEquals(true, d.minimal)
    }
}