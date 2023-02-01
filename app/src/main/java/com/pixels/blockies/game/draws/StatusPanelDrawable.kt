package com.pixels.blockies.game.draws

import android.graphics.Canvas
import com.pixels.blockies.game.draws.api.Drawable
import com.pixels.blockies.game.draws.enums.GameColor
import com.pixels.blockies.game.draws.enums.Letter
import com.pixels.blockies.game.draws.enums.Number
import com.pixels.blockies.game.game.GameContext
import com.pixels.blockies.game.game.Grid

/**
 * Draws the status panel on the top of the screen
 */
class StatusPanelDrawable : Drawable {
    /**
     * The whole screen width is used for the preview
     */
    private var width = -1
    private var blockHeight = -1
    private var blockWidth = -1
    private var blockStroke = -1

    /**
     * Preview and Score that are drawn on top
     */
    private val preview = FigurePreview()
    private val score = Score()

    /**
     * ViewContext with a lot of useful information
     */
    var context: ViewContext? = null

    init {
        context = DrawingView.Companion.getViewContext()
        blockHeight = context!!.getBlockHeight() / MINI_BLOCK_FACTOR
        blockWidth = context!!.getBlockWidth() / MINI_BLOCK_FACTOR
        blockStroke = context!!.getThickness() / MINI_BLOCK_FACTOR
        width = (context!!.getWidth() - context!!.getBorder()).toInt()
    }

    override fun draw(canvas: Canvas) {
        preview.draw(canvas)
        score.draw(canvas)
    }

    /**
     * Inner class that draws the preview on the top right corner
     */
    internal inner class FigurePreview : Drawable {
        override fun draw(canvas: Canvas) {
            // The the next rotatable that should be drawn
            val f = GameContext.PICKER.peek()
            val model = f!!.get()
            for (i in model!!.indices) {
                for (j in model[i]!!.indices) {
                    if (model[i]!![j] != Grid.Companion.EMPTY) {
                        val blockY = i * blockHeight + context!!.getBorder()
                        val blockX = j * blockWidth + width - model[i]!!.size * blockWidth
                        val b = BlockDrawable(blockX, blockY, blockWidth, blockHeight)
                        b.setSpecificColor(GameColor.Companion.forFigureNumber(model[i]!![j]))
                        b.setSpecificBlockStroke(blockStroke)
                        b.draw(canvas)
                    }
                }
            }
        }
    }

    /**
     * Inner class representing the score on the top left corner.
     * The score can reach a maximum of 999 lines, after that it starts
     * back at 0
     */
    internal inner class Score : Drawable {
        override fun draw(canvas: Canvas) {
            // Get the current score from GameContext
            var score = GameContext.getScore()

            // Factor to adjust the blocks (shrink)
            val adjustment = 2

            // get the lowest decimal digit of the score
            var oneth = score % 10

            // get the middle decimal digit of the score
            score /= 10
            var tenth = score % 10

            // get the highest decimal digit of the score
            score /= 10
            var hundredth = score

            // The gap between the digits
            var gap = 0
            if (hundredth > 0) {
                val modelHundreth: Array<BooleanArray> =
                    Number.Companion.forNumber(hundredth).getNumber()
                drawModel(canvas, modelHundreth, gap, adjustment)
                gap += (Number.Companion.COLUMN_COUNT + 1) * blockWidth / adjustment
            }
            if (tenth > 0) {
                val modelTenth: Array<BooleanArray> = Number.Companion.forNumber(tenth).getNumber()
                drawModel(canvas, modelTenth, gap, adjustment)
                gap += (Number.Companion.COLUMN_COUNT + 1) * blockWidth / adjustment
            }
            var modelOneth: Array<BooleanArray> = Number.Companion.forNumber(oneth).getNumber()
            drawModel(canvas, modelOneth, gap, adjustment)
            gap += (Number.Companion.COLUMN_COUNT + 1) * blockWidth / adjustment

            // slash
            val modelSlash: Array<BooleanArray> = Letter.Companion.forLetter('-')!!
                .getLetter()
            drawModel(canvas, modelSlash, gap, adjustment)
            gap += (Letter.Companion.COLUMN_COUNT + 1) * blockWidth / adjustment
            if (GameContext.HIGH_SCORE != null) {
                // Get the current highscore from GameContext
                score = GameContext.HIGH_SCORE!!.getScore()

                // get the lowest decimal digit of the score
                oneth = score % 10

                // get the middle decimal digit of the score
                score /= 10
                tenth = score % 10

                // get the highest decimal digit of the score
                score /= 10
                hundredth = score
                if (hundredth > 0) {
                    val modelHundreth: Array<BooleanArray> =
                        Number.Companion.forNumber(hundredth).getNumber()
                    drawModel(canvas, modelHundreth, gap, adjustment)
                    gap += (Number.Companion.COLUMN_COUNT + 1) * blockWidth / adjustment
                }
                if (tenth > 0) {
                    val modelTenth: Array<BooleanArray> =
                        Number.Companion.forNumber(tenth).getNumber()
                    drawModel(canvas, modelTenth, gap, adjustment)
                    gap += (Number.Companion.COLUMN_COUNT + 1) * blockWidth / adjustment
                }
                modelOneth = Number.Companion.forNumber(oneth).getNumber()
                drawModel(canvas, modelOneth, gap, adjustment)
            }
        }

        /**
         * Draw each digit as a block adjusted by 2
         * @param canvas
         * @param model
         * @param startX
         * @param adjustment
         */
        private fun drawModel(
            canvas: Canvas,
            model: Array<BooleanArray>,
            startX: Int,
            adjustment: Int
        ) {
            for (i in model.indices) {
                for (j in model[i].indices) {
                    if (model[i][j]) {
                        val blockY = i * (blockHeight / adjustment) + context!!.getBorder()
                        val blockX = j * (blockWidth / adjustment) + context!!.getBorder() + startX
                        val b = BlockDrawable(
                            blockX,
                            blockY,
                            blockWidth,
                            blockHeight,
                            adjustment.toFloat()
                        )
                        val f = GameContext.PICKER.peek()
                        b.setSpecificColor(GameColor.BLACK.getColor())
                        b.setSpecificBlockStroke(blockStroke / adjustment)
                        b.draw(canvas)
                    }
                }
            }
        }
    }

    companion object {
        /**
         * Factor that the blocks will be shrinked to,
         * in comparison to the standard-block size
         */
        const val MINI_BLOCK_FACTOR = 2
    }
}