package com.pixels.blockies.app.game.figures;

public class FigureZ extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {5, -1},
                    {5, 5},
                    {-1, 5}
            },
            {
                    {-1, 5, 5},
                    {5, 5, -1}
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
