package com.pixels.blockies.game.game.figures

import com.pixels.blockies.game.game.Grid

class FigureT : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(
            intArrayOf(Grid.Companion.EMPTY, 4, Grid.Companion.EMPTY),
            intArrayOf(4, 4, 4)
        ),
        arrayOf<IntArray?>(
            intArrayOf(4, Grid.Companion.EMPTY),
            intArrayOf(4, 4),
            intArrayOf(4, Grid.Companion.EMPTY)
        ),
        arrayOf<IntArray?>(
            intArrayOf(4, 4, 4),
            intArrayOf(Grid.Companion.EMPTY, 4, Grid.Companion.EMPTY)
        ),
        arrayOf<IntArray?>(
            intArrayOf(Grid.Companion.EMPTY, 4),
            intArrayOf(4, 4),
            intArrayOf(Grid.Companion.EMPTY, 4)
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