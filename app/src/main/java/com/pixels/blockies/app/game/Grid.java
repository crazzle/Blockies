package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;

/**
 * Created by keinhoerster on 3/17/14.
 */
public class Grid {
    private static Grid grid = null;

    private int[][] logicalGrid = new int[StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT][StaticGameEnvironment.VERTICAL_BLOCK_COUNT];

    private Grid() {
        initLogicalGrid();
    }

    public static Grid getInstance() {
        if (grid == null) {
            grid = new Grid();
        }
        return grid;
    }

    private void initLogicalGrid() {
        for (int i = 0; i < logicalGrid.length; i++) {
            for (int j = 0; j < logicalGrid[i].length; j++) {
                logicalGrid[i][j] = 0;
            }
        }
    }

    public void add(int x, int y, int value) {
        logicalGrid[x][y] = value;
    }

    public void remove(int x, int y) {
        logicalGrid[x][y] = 0;
    }

    public int getPositionValue(int x, int y) {
        return logicalGrid[x][y];
    }

}
