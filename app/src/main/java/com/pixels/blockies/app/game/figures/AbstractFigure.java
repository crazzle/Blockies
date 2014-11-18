package com.pixels.blockies.app.game.figures;

public abstract class AbstractFigure implements Rotatable {
    private int currentRotation = 0;

    protected abstract int getFigureCount();

    protected int getCurrentRotation() {
        return currentRotation;
    }

    protected int getNextRotation() {
        if (currentRotation < getFigureCount() - 1) {
            return currentRotation+1;
        } else {
            return 0;
        }
    }

    public void rotate() {
        if (currentRotation < getFigureCount() - 1) {
            currentRotation++;
        } else {
            currentRotation = 0;
        }
    }

}
