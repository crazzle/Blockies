package com.pixels.blockies.app.draws;

import android.graphics.Color;

/**
 * Created by mark on 09.11.14.
 */
public enum GameColor {
    GREEN(104, 195, 163),
    PURPLE(155, 89, 182),
    ORANGE(235, 149, 50),
    YELLOW(245, 215, 110),
    RED(210, 77, 87),
    PINK(224, 130, 131),
    BLUE(52, 152, 219),
    WHITE(228, 241, 254),
    GRAY(108,122,137),
    BLACK(34,49,63);

    private int color;

    private GameColor(int r, int g, int b) {
        this.color = Color.rgb(r, g, b);
    }

    public int getColor() {
        return color;
    }

    public static int forFigureNumber(int positionValue) {
        return values()[positionValue].getColor();
    }
}
