package com.pixels.blockies.game.game.figures;

import com.pixels.blockies.game.game.Grid;

public class FigureJ extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {1, Grid.EMPTY, Grid.EMPTY},
                    {1, 1, 1}
            },
            {
                    {1, 1},
                    {1, Grid.EMPTY},
                    {1, Grid.EMPTY}
            },
            {
                    {1, 1, 1},
                    {Grid.EMPTY, Grid.EMPTY, 1}
            },
            {
                    {Grid.EMPTY, 1},
                    {Grid.EMPTY, 1},
                    {1, 1}
            }
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
