package com.pixels.blockies.app.game;

import com.pixels.blockies.app.game.figures.Picker;

/**
 * The GameContext holds the current games state as well
 * as game specific information
 */
public class GameContext {
    /**
     * Defines how large the grid is
     */
    public static final int HORIZONTAL_BLOCK_COUNT = 10;
    public static final int VERTICAL_BLOCK_COUNT = 20;

    /**
     * The picker is responsible for picking a new figure
     */
    public static final Picker PICKER = new Picker();

    /**
     * The current score (counter of completed lines)
     */
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
