package com.pixels.blockies.game.game.figures

import com.pixels.blockies.game.game.Grid

class FigureL : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(
            intArrayOf(Grid.Companion.EMPTY, Grid.Companion.EMPTY, 2),
            intArrayOf(2, 2, 2)
        ),
        arrayOf<IntArray?>(
            intArrayOf(2, Grid.Companion.EMPTY),
            intArrayOf(2, Grid.Companion.EMPTY),
            intArrayOf(2, 2)
        ),
        arrayOf<IntArray?>(
            intArrayOf(2, 2, 2),
            intArrayOf(2, Grid.Companion.EMPTY, Grid.Companion.EMPTY)
        ),
        arrayOf<IntArray?>(
            intArrayOf(2, 2),
            intArrayOf(Grid.Companion.EMPTY, 2),
            intArrayOf(Grid.Companion.EMPTY, 2)
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