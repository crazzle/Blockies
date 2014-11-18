package com.pixels.blockies.app.game.figures;

import com.pixels.blockies.app.game.Grid;

public class FigureZ extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {5, Grid.EMPTY},
                    {5, 5},
                    {Grid.EMPTY, 5}
            },
            {
                    {Grid.EMPTY, 5, 5},
                    {5, 5, Grid.EMPTY}
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
