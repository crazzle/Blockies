package com.pixels.blockies.app.game;

/**
 * Created by keinhoerster on 3/18/14.
 */
public class Block {
    int x = -1;
    int y = -1;
    private int[][] compound = null;
    public Block(int[][] compound) {
        this.compound = compound;
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
        return compound[y][x];
    }

    public int getOffsetY() {
        return compound.length;
    }

    public int getOffsetX() {
        return compound[0].length;
    }
}
