package com.pixels.blockies.game.game.figures

class FigureI : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(intArrayOf(0, 0, 0, 0)),
        arrayOf<IntArray?>(intArrayOf(0), intArrayOf(0), intArrayOf(0), intArrayOf(0))
    )

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