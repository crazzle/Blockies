package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;

/**
 * Created by keinhoerster on 3/17/14.
 */
public class Grid {
    private static  Grid grid = null;

    private int[][] logicalGrid = new int[StaticGameEnvironment.VERTICAL_BLOCK_COUNT][StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT];

    public static Grid getInstance(){
        if(grid == null){
            grid = new Grid();
        }
        return grid;
    }

    private Grid(){
        initLogicalGrid();
    }

    private void initLogicalGrid() {
        for(int i = 0; i < logicalGrid.length; i++){
            for(int j = 0; j < logicalGrid[i].length; j++){
                logicalGrid[i][j] = 0;
            }
        }
    }

    public void add(int x, int y) {
        logicalGrid[x][y] = 1;
    }

    public void remove(int x, int y) {
        logicalGrid[x][y] = 0;
    }

    public int getPositionValue(int x, int y){
        return logicalGrid[x][y];
    }
}
