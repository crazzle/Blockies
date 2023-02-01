package com.pixels.blockies.game.draws

import android.graphics.Canvas
import com.pixels.blockies.game.draws.api.Drawable
import com.pixels.blockies.game.draws.enums.GameColor
import com.pixels.blockies.game.draws.enums.Letter

/**
 * Draws the screen that is shown after a game is over
 */
class RestartScreenDrawable : Drawable {
    private var xCenter = -1
    private var yCenter = -1
    private var blockHeight = -1
    private var blockWidth = -1
    private var blockStroke = -1
    private val TAP = "TAP!"
    override fun draw(canvas: Canvas) {
        var next = 0
        for (c in TAP.toCharArray()) {
            drawChar(canvas, c, xCenter + next, yCenter)
            next = (next + TAP.length + 1) * blockWidth
        }
    }

    fun drawChar(canvas: Canvas, letter: Char, xPos: Int, yPos: Int) {
        val model: Array<BooleanArray> = Letter.Companion.forLetter(letter)!!.getLetter()
        for (i in model.indices) {
            for (j in model[i].indices) {
                if (model[i][j]) {
                    val blockY = i * blockHeight + yPos
                    val blockX = j * blockWidth + xPos
                    val b = BlockDrawable(blockX, blockY, blockWidth, blockHeight)
                    b.setSpecificColor(GameColor.BLACK.getColor())
                    b.setSpecificBlockStroke(blockStroke)
                    b.draw(canvas)
                }
            }
        }
    }

    init {
        val context: ViewContext? = DrawingView.Companion.getViewContext()
        if (context != null) {
            blockHeight = (context.getBlockHeight() / MINI_BLOCK_FACTOR).toInt()
            blockWidth = (context.getBlockWidth() / MINI_BLOCK_FACTOR).toInt()
            blockStroke = (context.getThickness() / MINI_BLOCK_FACTOR).toInt()
            xCenter =
                context.getWidth()
                    .toInt() / 2 - Letter.Companion.COLUMN_COUNT * (TAP.length - 1) * blockWidth
            yCenter = context.getHeight().toInt() / 2 - Letter.Companion.COLUMN_COUNT * blockHeight
        }
    }

    companion object {
        const val MINI_BLOCK_FACTOR = 1.85f
    }
}