package com.pixels.blockies.app.game.figures;

/**
 * Created by mark on 02.11.14.
 */
public abstract class AbstractFigure implements Rotatable {
    int currentRotation = 0;

    protected abstract int getFigureCount();

    public void rotate() {
        if (currentRotation < getFigureCount() - 1) {
            currentRotation++;
        } else {
            currentRotation = 0;
        }
    }
}
