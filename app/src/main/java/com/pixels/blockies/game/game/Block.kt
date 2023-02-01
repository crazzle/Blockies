package com.pixels.blockies.game.game;

import com.pixels.blockies.game.game.figures.Rotatable;

/**
 * Represents a logical block with a coordinate in the grid
 * and a figure that can be rotated
 */
public class Block {
    int x = -1;
    int y = -1;
    Rotatable figure = null;

    public Block(Rotatable figure)
    {
        this.figure = figure;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getInner(int x, int y) {
        return figure.get()[y][x];
    }

    public int getOffsetY() {
        return figure.get().length;
    }

    public int getOffsetX() {
        return figure.get()[0].length;
    }

    public void rotate(){
        figure.rotate();
    }

    public int getRotatedInner(int x, int y){
        return figure.getNext()[y][x];
    }

    public int getRotatedOffsetY() {
        return figure.getNext().length;
    }

    public int getRotatedOffsetX() {
        return figure.getNext()[0].length;
    }

}
