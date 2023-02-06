package com.pixels.blockies.game.game

import com.pixels.blockies.game.game.figures.FigureI
import com.pixels.blockies.game.game.figures.FigureJ
import com.pixels.blockies.game.game.figures.FigureSingleDot
import org.junit.Assert.assertEquals
import org.junit.Test

internal class BlockTest {
    @Test
    fun testBlockSizesAreCorrectX() {
        assertEquals(4, Block(FigureI()).getOffsetX())
        assertEquals(3, Block(FigureJ()).getOffsetX())
        assertEquals(1, Block(FigureSingleDot()).getOffsetX())
    }
    @Test
    fun testBlockSizesAreCorrectY() {
        assertEquals(1, Block(FigureI()).getOffsetY())
        assertEquals(2, Block(FigureJ()).getOffsetY())
        assertEquals(1, Block(FigureSingleDot()).getOffsetY())
    }
}