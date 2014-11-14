package com.pixels.blockies.app.game;

import com.pixels.blockies.app.game.figures.Picker;

public class GameContext {
    public static final int HORIZONTAL_BLOCK_COUNT = 10;
    public static final int VERTICAL_BLOCK_COUNT = 20;
    public static final Picker PICKER = new Picker();

    private static int score = 0;

    public static synchronized void addToScore(int count){
        score+=count;
    }

    public static synchronized int getScore(){
        return score;
    }

    public static synchronized void reset(){
        score = 0;
    }
}
