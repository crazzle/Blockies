package com.pixels.blockies.game.draws

import android.graphics.Canvas
import android.graphics.Paint
import com.pixels.blockies.game.draws.api.Drawable
import com.pixels.blockies.game.draws.enums.GameColor

/**
 * Draws a single block on a specific location by given width and height.
 * It can also be adjusted (shrink/expand) by a factor. That way the proportions
 * to its stroke thickness will be kept.
 */
class BlockDrawable : Drawable {
    private val paint = Paint()

    /**
     * The cooridinates of the block
     */
    private var x = 0
    private var y = 0

    /**
     * The color of the block
     */
    private var specificColor = -1

    /**
     * The stroke size of the block
     */
    private var specificBlockStroke = -1

    /**
     * The adjustment to scale the block up or down
     */
    private var adjustment = -1f

    /**
     * Width and height of the block
     */
    private var blockWidth = -1
    private var blockHeight = -1

    /**
     * Blockdrawable that keeps its size as defined by blockwidth and blockheight
     *
     * @param x
     * @param y
     * @param blockwidth
     * @param blockHeight
     */
    constructor(x: Int, y: Int, blockwidth: Int, blockHeight: Int) {
        this.x = x
        this.y = y
        adjustment = 1f
        blockWidth = blockwidth
        this.blockHeight = blockHeight
    }

    /**
     * Blockdrawable that adjusts its size and stroke by a given factor
     *
     * @param x
     * @param y
     * @param blockwidth
     * @param blockHeight
     * @param adjustment
     */
    constructor(x: Int, y: Int, blockwidth: Int, blockHeight: Int, adjustment: Float) {
        this.x = x
        this.y = y
        this.adjustment = adjustment
        blockWidth = blockwidth
        this.blockHeight = blockHeight
    }

    override fun draw(canvas: Canvas) {
        paint.style = Paint.Style.FILL
        paint.color = specificColor
        canvas.drawRect(
            x.toFloat(),
            y.toFloat(),
            x + blockWidth / adjustment,
            y + blockHeight / adjustment,
            paint
        )
        drawStroke(canvas)
    }

    fun drawStroke(canvas: Canvas) {
        paint.strokeWidth = specificBlockStroke.toFloat()
        paint.style = Paint.Style.STROKE
        paint.color = GameColor.WHITE.getColor()
        canvas.drawRect(
            x.toFloat(),
            y.toFloat(),
            x + blockWidth / adjustment,
            y + blockHeight / adjustment,
            paint
        )
    }

    fun setSpecificColor(specificColor: Int) {
        this.specificColor = specificColor
    }

    fun setSpecificBlockStroke(specificBlockStroke: Int) {
        this.specificBlockStroke = specificBlockStroke
    }
}