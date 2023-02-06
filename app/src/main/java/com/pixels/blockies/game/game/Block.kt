package com.pixels.blockies.game.game

import com.pixels.blockies.game.game.figures.Rotatable

/**
 * Represents a logical block with a coordinate in the grid
 * and a figure that can be rotated
 */
class Block(figure: Rotatable?) {
    var x = -1
    private var y = -1
    private var figure: Rotatable? = null

    init {
        this.figure = figure
    }

    fun getY(): Int {
        return y
    }

    fun setY(y: Int) {
        this.y = y
    }

    fun getInner(x: Int, y: Int): Int {
        return figure!!.get()[y]!![x]
    }

    fun getOffsetY(): Int {
        return figure!!.get().size
    }

    fun getOffsetX(): Int {
        return figure!!.get()[0]!!.size
    }

    fun rotate() {
        figure!!.rotate()
    }

    fun getRotatedInner(x: Int, y: Int): Int {
        return figure!!.getNext()[y]!![x]
    }

    fun getRotatedOffsetY(): Int {
        return figure!!.getNext().size
    }

    fun getRotatedOffsetX(): Int {
        return figure!!.getNext()[0]!!.size
    }
}