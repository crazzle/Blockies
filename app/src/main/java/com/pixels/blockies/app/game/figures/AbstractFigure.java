package com.pixels.blockies.app.game.figures;

/**
 * Created by keinmark on 30.04.14.
 */
public abstract class AbstractFigure implements Rotatable{

    protected int[][] blocks = new int[4][4];

    public abstract void initialize();
    public void rotate(){

    }
}
