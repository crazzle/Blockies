package com.pixels.blockies.game.game.figures

class FigureSingleDot : AbstractFigure() {
    private val figures = arrayOf(arrayOf<IntArray?>(intArrayOf(8)))
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