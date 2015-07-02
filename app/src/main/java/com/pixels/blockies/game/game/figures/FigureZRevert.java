package com.pixels.blockies.game.game.figures;

import com.pixels.blockies.game.game.Grid;

public class FigureZRevert extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {Grid.EMPTY, 6},
                    {6, 6},
                    {6, Grid.EMPTY}
            },
            {
                    {6, 6, Grid.EMPTY},
                    {Grid.EMPTY, 6, 6}
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
