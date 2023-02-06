package com.pixels.blockies.game.game.figures

import com.pixels.blockies.game.game.Grid

class FigureJ : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(
            intArrayOf(1, Grid.Companion.EMPTY, Grid.Companion.EMPTY),
            intArrayOf(1, 1, 1)
        ),
        arrayOf<IntArray?>(
            intArrayOf(1, 1),
            intArrayOf(1, Grid.Companion.EMPTY),
            intArrayOf(1, Grid.Companion.EMPTY)
        ),
        arrayOf<IntArray?>(
            intArrayOf(1, 1, 1),
            intArrayOf(Grid.Companion.EMPTY, Grid.Companion.EMPTY, 1)
        ),
        arrayOf<IntArray?>(
            intArrayOf(Grid.Companion.EMPTY, 1),
            intArrayOf(Grid.Companion.EMPTY, 1),
            intArrayOf(1, 1)
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