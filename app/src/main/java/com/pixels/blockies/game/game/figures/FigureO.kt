package com.pixels.blockies.game.game.figures

class FigureO : AbstractFigure() {
    private val figures = arrayOf(arrayOf<IntArray?>(intArrayOf(3, 3), intArrayOf(3, 3)))
    override fun getFigureCount(): Int {
        return figures.size
    }

    override fun get(): Array<IntArray?> {
        return figures[currentRotation]
    }

    override fun getNext(): Array<IntArray?> {
        return figures[nextRotation]
    }
}