package com.pixels.blockies.app.game.figures;

/**
 * Created by keinmark on 30.04.14.
 */
public class FigureJ extends AbstractFigure {

    private int[][][] figures = new int[][][]{
            {
                    {1, 0, 0},
                    {1, 1, 1},
                    {0, 0, 0}
            },
            {
                    {0, 1, 1},
                    {0, 1, 0},
                    {0, 1, 0}
            },
            {
                    {1, 1, 1},
                    {0, 0, 1},
                    {0, 0, 0}
            },
            {
                    {0, 0, 1},
                    {0, 0, 1},
                    {0, 1, 1}
            }
    };


    @Override
    protected int getFigureCount() {
        return figures.length;
    }
}
