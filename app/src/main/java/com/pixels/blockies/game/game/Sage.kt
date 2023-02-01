package com.pixels.blockies.game.game

/**
 * The sages know how to check things
 */
class Sage {
    var grid: Grid? = Grid.Companion.getInstance()

    /**
     * Checks for completed lines and returns a list with the index
     * of all completed lines
     * @return
     */
    fun checkForCompleteLines(): List<Int> {
        val lines: MutableList<Int> = ArrayList()
        for (i in 0 until GameContext.VERTICAL_BLOCK_COUNT) {
            var sum = 0
            for (j in 0 until GameContext.HORIZONTAL_BLOCK_COUNT) {
                val `val` = grid!!.getPositionValue(j, i)
                val num = if (`val` > -1) 1 else 0
                sum += num
            }
            if (sum == GameContext.HORIZONTAL_BLOCK_COUNT) {
                lines.add(i)
            }
        }
        return lines
    }
}