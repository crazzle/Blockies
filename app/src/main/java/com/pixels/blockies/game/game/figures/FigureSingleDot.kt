package com.pixels.blockies.game.game.figures;

public class FigureSingleDot extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {8}
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
