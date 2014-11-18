package com.pixels.blockies.app.game.figures;

import com.pixels.blockies.app.game.Grid;

public class FigureT extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {Grid.EMPTY, 4, Grid.EMPTY},
                    {4, 4, 4}
            },
            {
                    {4, Grid.EMPTY},
                    {4, 4},
                    {4, Grid.EMPTY}
            },
            {
                    {4, 4, 4},
                    {Grid.EMPTY, 4, Grid.EMPTY}
            },
            {
                    {Grid.EMPTY, 4},
                    {4, 4},
                    {Grid.EMPTY, 4}
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
