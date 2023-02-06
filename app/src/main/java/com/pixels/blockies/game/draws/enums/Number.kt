package com.pixels.blockies.game.draws.enums

/**
 * Enum with all numbers that are
 * drawn using blocks
 */
enum class Number(private val field: Array<BooleanArray>) {
    ZERO(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true)
        )
    ),
    ONE(
        arrayOf(
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true)
        )
    ),
    TWO(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true)
        )
    ),
    THREE(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true)
        )
    ),
    FOUR(
        arrayOf(
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true)
        )
    ),
    FIVE(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true)
        )
    ),
    SIX(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, false),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true)
        )
    ),
    SEVEN(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(false, false, true)
        )
    ),
    EIGHT(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true)
        )
    ),
    NINE(
        arrayOf(
            booleanArrayOf(true, true, true),
            booleanArrayOf(true, false, true),
            booleanArrayOf(true, true, true),
            booleanArrayOf(false, false, true),
            booleanArrayOf(true, true, true)
        )
    );

    fun getNumber(): Array<BooleanArray> {
        return field
    }

    companion object {
        const val COLUMN_COUNT = 3
        fun forNumber(number: Int): Number {
            return values()[number]
        }
    }
}