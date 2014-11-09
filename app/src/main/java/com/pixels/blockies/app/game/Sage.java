package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mark on 09.11.14.
 */
public class Sage {
    Grid grid = Grid.getInstance();

    public List<Integer> checkForCompleteLines(){
        List<Integer> lines = new ArrayList<Integer>();
        for(int i = 0; i < StaticGameEnvironment.VERTICAL_BLOCK_COUNT; i++){
            int sum = 0;
            for(int j = 0; j < StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT; j++){
                int val = grid.getPositionValue(j, i);
                int num = val > -1 ? 1 : 0;
                sum+=num;
            }
            if(sum == StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT){
                lines.add(i);
            }
        }
        return lines;
    }
}
