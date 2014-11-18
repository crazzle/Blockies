package com.pixels.blockies.app.game.figures;

public class FigureO extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {3, 3},
                    {3, 3},
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
