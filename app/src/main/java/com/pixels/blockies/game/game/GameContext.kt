package com.pixels.blockies.game.game

import com.pixels.blockies.game.game.figures.Picker

/**
 * The GameContext holds the current games state as well
 * as game specific information
 */
object GameContext {
    /**
     * Defines how large the grid is
     */
    const val HORIZONTAL_BLOCK_COUNT = 10
    const val VERTICAL_BLOCK_COUNT = 20

    /**
     * The picker is responsible for picking a new figure
     */
    val PICKER = Picker()

    /**
     * The highscore is responsible for storing the highest score
     */
    var HIGH_SCORE: HighScore? = null

    /**
     * The current score (counter of completed lines)
     */
    private var score = 0
    @Synchronized
    fun addToScore(count: Int) {
        score += count
    }

    @Synchronized
    fun getScore(): Int {
        return score
    }

    @Synchronized
    fun reset() {
        score = 0
    }
}