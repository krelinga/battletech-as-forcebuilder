package net.slothdev.battletech.`as`.forcebuilder

import kotlin.test.*
import org.junit.jupiter.api.*

internal class MiniatureTest {
    @Nested inner class Constructor {
        @Test fun `positive pv is OK`() {
            Miniature("1", Color.GREEN, GameUnit("foo", 1))
        }
        @Test fun `zero pv is OK`() {
            Miniature("1", Color.GREEN, GameUnit("foo", 0))
        }
        @Test fun `negative pv throws exception`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("1", Color.GREEN, GameUnit("foo", -1))
            }
        }
    }

    @Nested
    inner class Id {
        @Test
        fun `empty ID is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains space is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature(" ", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature(" A", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A ", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A A", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains tab is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("\t", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("\tA", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A\t", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A\tA", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains newline is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("\n", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("\nA", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A\n", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A\nA", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains return is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("\r", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("\rA", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A\r", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A\rA", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains lowercase is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("aa", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("aA", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("Aa", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains zero is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("0", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("10", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("01", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that contains underbar is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("_", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A_", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("_A", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A_A", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that starts with dash is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("-", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("-A", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("-1", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `ID that ends with dash is rejected`() {
            assertFailsWith<IllegalArgumentException> {
                Miniature("-", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("A-", Color.GREEN, GameUnit("foo", 1))
            }
            assertFailsWith<IllegalArgumentException> {
                Miniature("1-", Color.GREEN, GameUnit("foo", 1))
            }
        }

        @Test
        fun `Legal IDs are fine`() {
            Miniature("A", Color.GREEN, GameUnit("foo", 1))
            Miniature("AV", Color.GREEN, GameUnit("foo", 1))
            Miniature("AO", Color.GREEN, GameUnit("foo", 1))
            Miniature("A1", Color.GREEN, GameUnit("foo", 1))
            Miniature("A-1", Color.GREEN, GameUnit("foo", 1))
        }
    }
}