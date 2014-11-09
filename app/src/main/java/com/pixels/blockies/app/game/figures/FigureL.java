package com.pixels.blockies.app.game.figures;

/**
 * Created by keinmark on 30.04.14.
 */
public class FigureL extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {-1, -1, 2},
                    {2, 2, 2}
            },
            {
                    {2, -1},
                    {2, -1},
                    {2, 2}
            },
            {
                    {2, 2, 2},
                    {2, -1, -1}
            },
            {
                    {2, 2},
                    {-1, 2},
                    {-1, 2}
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
