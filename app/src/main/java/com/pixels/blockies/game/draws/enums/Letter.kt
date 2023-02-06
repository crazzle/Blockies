package com.pixels.blockies.game.draws.enums

/**
 * Enum with all letters that are
 * drawn using blocks
 */
enum class Letter(private val field: Array<BooleanArray>, private val letter: Char) {
    T(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false)
        ), 'T'
    ),
    A(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, false, true)
        ), 'A'
    ),
    B(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true)
        ), 'B'
    ),
    P(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, false, false)
        ), 'P'
    ),
    L(
        arrayOf(
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true)
        ), 'L'
    ),
    E(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true)
        ), 'E'
    ),
    S(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true)
        ), 'S'
    ),
    EXCLAMATION_MARK(
        arrayOf(
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, false, false),
            booleanArrayOf(false, true, false)
        ), '!'
    ),
    DOUBLE_POINT(
        arrayOf(
            booleanArrayOf(false, false, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, false, false),
            booleanArrayOf(false, true, false),
            booleanArrayOf(false, false, false)
        ), ':'
    ),
    DASH(
        arrayOf(
            booleanArrayOf(false, false, false),
            booleanArrayOf(false, false, false),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, false),
            booleanArrayOf(false, false, false)
        ), '-'
    );

    fun getLetter(): Array<BooleanArray> {
        return field
    }

    companion object {
        const val COLUMN_COUNT = 3
        fun forLetter(letter: Char): Letter? {
            var toReturn: Letter? = null
            for (l in values()) {
                if (l.letter == letter) {
                    toReturn = l
                }
            }
            return toReturn
        }
    }
}