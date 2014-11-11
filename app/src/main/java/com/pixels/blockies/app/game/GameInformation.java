package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.BlockMover;
import com.pixels.blockies.app.game.figures.Picker;

/**
 * Created by mark on 11.11.14.
 */
public class GameInformation {
    private static int score = 0;

    public static synchronized void addToScore(int count){
        score+=count;
    }

    public static synchronized int getScore(){
        return score;
    }

}
