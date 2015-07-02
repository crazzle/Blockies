package com.pixels.blockies.game.game.figures;

import com.pixels.blockies.game.game.Grid;

public class FigureSplitLine extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {7, Grid.EMPTY, 7}
            },
            {
                    {7},
                    {Grid.EMPTY},
                    {7}
            },
    };

    @Override
    protected int getFigureCount() {
        return figures.length;
    }

    @Override
    public int[][] get() {
        return figures[getCurrentRotation()];
    }

    @Override
    public int[][] getNext() {
        return figures[getNextRotation()];
    }
}
