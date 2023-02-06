package com.pixels.blockies.game.game.figures

import com.pixels.blockies.game.game.Grid

class FigureSplitLine : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(intArrayOf(7, Grid.Companion.EMPTY, 7)),
        arrayOf<IntArray?>(intArrayOf(7), intArrayOf(Grid.Companion.EMPTY), intArrayOf(7))
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