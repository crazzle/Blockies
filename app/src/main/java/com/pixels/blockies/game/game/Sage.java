package com.pixels.blockies.game.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The sages know how to check things
 */
public class Sage {
    Grid grid = Grid.getInstance();

    /**
     * Checks for completed lines and returns a list with the index
     * of all completed lines
     * @return
     */
    public List<Integer> checkForCompleteLines(){
        List<Integer> lines = new ArrayList<Integer>();
        for(int i = 0; i < GameContext.VERTICAL_BLOCK_COUNT; i++){
            int sum = 0;
            for(int j = 0; j < GameContext.HORIZONTAL_BLOCK_COUNT; j++){
                int val = grid.getPositionValue(j, i);
                int num = val > -1 ? 1 : 0;
                sum+=num;
            }
            if(sum == GameContext.HORIZONTAL_BLOCK_COUNT){
                lines.add(i);
            }
        }
        return lines;
    }
}
