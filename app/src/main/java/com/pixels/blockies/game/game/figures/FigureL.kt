package com.pixels.blockies.game.game.figures;

import com.pixels.blockies.game.game.Grid;

public class FigureL extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {Grid.EMPTY, Grid.EMPTY, 2},
                    {2, 2, 2}
            },
            {
                    {2, Grid.EMPTY},
                    {2, Grid.EMPTY},
                    {2, 2}
            },
            {
                    {2, 2, 2},
                    {2, Grid.EMPTY, Grid.EMPTY}
            },
            {
                    {2, 2},
                    {Grid.EMPTY, 2},
                    {Grid.EMPTY, 2}
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
