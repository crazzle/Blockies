package com.pixels.blockies.game.draws

/**
 * The context that holds important information
 * in order to draw all the block correctly
 */
class ViewContext {
    private var border = -1
    private var thickness = -1
    private var width = -1f
    private var height = -1f
    private var blockHeight = -1
    private var blockWidth = -1
    fun getBorder(): Int {
        return border
    }

    fun setBorder(border: Int) {
        this.border = border
    }

    fun getThickness(): Int {
        return thickness
    }

    fun setStrokeThickness(thickness: Int) {
        this.thickness = thickness
    }

    fun getWidth(): Float {
        return width
    }

    fun setWidth(width: Float) {
        this.width = width
    }

    fun getHeight(): Float {
        return height
    }

    fun setHeight(height: Float) {
        this.height = height
    }

    fun getBlockHeight(): Int {
        return blockHeight
    }

    fun setBlockHeight(blockHeight: Int) {
        this.blockHeight = blockHeight
    }

    fun getBlockWidth(): Int {
        return blockWidth
    }

    fun setBlockWidth(blockWidth: Int) {
        this.blockWidth = blockWidth
    }
}