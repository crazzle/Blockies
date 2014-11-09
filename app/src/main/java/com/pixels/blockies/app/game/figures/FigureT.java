package com.pixels.blockies.app.game.figures;

public class FigureT extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {-1, 4, -1},
                    {4, 4, 4}
            },
            {
                    {4, -1},
                    {4, 4},
                    {4, -1}
            },
            {
                    {4, 4, 4},
                    {-1, 4, -1}
            },
            {
                    {-1, 4},
                    {4, 4},
                    {-1, 4}
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
