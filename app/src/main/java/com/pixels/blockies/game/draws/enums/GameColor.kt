package com.pixels.blockies.game.draws.enums

import android.graphics.Color

/**
 * Enum with all colors that are used in the game
 */
enum class GameColor(r: Int, g: Int, b: Int) {
    GREEN(104, 195, 163), PURPLE(155, 89, 182), ORANGE(235, 149, 50), YELLOW(
        245,
        215,
        110
    ),
    RED(210, 77, 87), PINK(224, 130, 131), SAN_MARINO_BLUE(68, 108, 179), CARBARET(
        210,
        82,
        127
    ),
    HOKI(103, 128, 159), BLUE(52, 152, 219), WHITE(228, 241, 254), BLACK(52, 73, 94), BURNT_ORANGE(
        211,
        84,
        0
    );

    private val color: Int

    init {
        color = Color.rgb(r, g, b)
    }

    fun getColor(): Int {
        return color
    }

    companion object {
        fun forFigureNumber(positionValue: Int): Int {
            return values()[positionValue].getColor()
        }
    }
}