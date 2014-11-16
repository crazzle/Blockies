package com.pixels.blockies.app.draws;

/**
 * Created by mark on 16.11.14.
 */
public class ViewContext {
    private int border = -1;
    private int thickness = -1;
    private float width = -1;
    private float height = -1;
    private int blockHeight = -1;
    private int blockWidth =  -1;

    public int getBorder() {
        return border;
    }

    public void setBorder(int border) {
        this.border = border;
    }

    public int getThickness() {
        return thickness;
    }

    public void setStrokeThickness(int thickness) {
        this.thickness = thickness;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getBlockHeight() {
        return blockHeight;
    }

    public void setBlockHeight(int blockHeight) {
        this.blockHeight = blockHeight;
    }

    public int getBlockWidth() {
        return blockWidth;
    }

    public void setBlockWidth(int blockWidth) {
        this.blockWidth = blockWidth;
    }
}
