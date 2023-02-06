package com.pixels.blockies.game.game.figures

import com.pixels.blockies.game.game.Grid

class FigureZRevert : AbstractFigure() {
    private val figures = arrayOf(
        arrayOf<IntArray?>(
            intArrayOf(Grid.Companion.EMPTY, 6),
            intArrayOf(6, 6),
            intArrayOf(6, Grid.Companion.EMPTY)
        ),
        arrayOf<IntArray?>(
            intArrayOf(6, 6, Grid.Companion.EMPTY),
            intArrayOf(Grid.Companion.EMPTY, 6, 6)
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