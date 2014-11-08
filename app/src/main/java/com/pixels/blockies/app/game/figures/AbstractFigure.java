package com.pixels.blockies.app.game.figures;

/**
 * Created by mark on 02.11.14.
 */
public abstract class AbstractFigure implements Rotatable {
    private int currentRotation = 0;

    protected abstract int getFigureCount();

    protected int getCurrentRotation() {
        return currentRotation;
    }

    public void rotate() {
        if (currentRotation < getFigureCount() - 1) {
            currentRotation++;
        } else {
            currentRotation = 0;
        }
    }

}
