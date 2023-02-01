package com.pixels.blockies.game.game.figures

import com.pixels.blockies.game.game.Grid

class FigureZ : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(
            intArrayOf(5, Grid.Companion.EMPTY),
            intArrayOf(5, 5),
            intArrayOf(Grid.Companion.EMPTY, 5)
        ),
        arrayOf<IntArray?>(
            intArrayOf(Grid.Companion.EMPTY, 5, 5),
            intArrayOf(5, 5, Grid.Companion.EMPTY)
        )
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