package com.pixels.blockies.game.draws

import android.graphics.Canvas
import com.pixels.blockies.game.draws.api.Drawable
import com.pixels.blockies.game.draws.enums.GameColor
import com.pixels.blockies.game.game.GameContext
import com.pixels.blockies.game.game.Grid

/**
 * Draws the underlying logical grid of the game
 */
class GridDrawable private constructor() : Drawable {
    /**
     * Array of block drawables which corresponds to the logical grid
     */
    var blockDrawables =
        Array(GameContext.HORIZONTAL_BLOCK_COUNT) { arrayOfNulls<BlockDrawable>(GameContext.VERTICAL_BLOCK_COUNT) }

    /**
     * The logical grid which is used by the block-mover
     */
    var logicalGrid: Grid? = Grid.Companion.getInstance()

    /**
     * The ViewContext with all the important information about sizes, resolution and much more
     */
    var context: ViewContext? = null

    /**
     * Private constructor for singleton access
     */
    init {
        context = DrawingView.Companion.getViewContext()
    }

    override fun draw(canvas: Canvas) {
        updateFromLogicalGrid()
        for (i in blockDrawables.indices) {
            for (j in blockDrawables[i].indices) {
                if (blockDrawables[i][j] != null) {
                    blockDrawables[i][j]!!.draw(canvas)
                }
            }
        }
    }

    /**
     * Updates the array of block drawables with the current information from the local grid
     */
    private fun updateFromLogicalGrid() {
        for (i in blockDrawables.indices) {
            val line = blockDrawables[i]
            for (j in line.indices) {
                val fieldValue = logicalGrid!!.getPositionValue(i, j)
                if (fieldValue > Grid.Companion.EMPTY) {
                    val blockColor: Int = GameColor.Companion.forFigureNumber(fieldValue)
                    val blockX = i * context!!.getBlockWidth() + context!!.getBorder()
                    val blockY = j * context!!.getBlockHeight() + context!!.getBorder()
                    val b =
                        BlockDrawable(blockX, blockY, context!!.getBlockWidth(), context!!.getBlockHeight())
                    b.setSpecificColor(blockColor)
                    b.setSpecificBlockStroke(context!!.getThickness())
                    line[j] = b
                } else {
                    line[j] = null
                }
            }
        }
    }

    companion object {
        /**
         * Provide the GridDrawable as a singleton
         */
        private var grid: GridDrawable? = null

        /**
         * Factory method to provide the singleton instance
         *
         * @return
         */
        fun getInstance(): GridDrawable? {
            if (grid == null) {
                grid = GridDrawable()
            }
            return grid
        }
    }
}