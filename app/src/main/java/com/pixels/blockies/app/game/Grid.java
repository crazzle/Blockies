package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public synchronized void initLogicalGrid() {
        for (int i = 0; i < logicalGrid.length; i++) {
            for (int j = 0; j < logicalGrid[i].length; j++) {
                logicalGrid[i][j] = -1;
            }
        }
    }

    public synchronized void add(int x, int y, int value) {
        logicalGrid[x][y] = value;
    }

    public synchronized void remove(int x, int y) {
        logicalGrid[x][y] = -1;
    }

    public synchronized int getPositionValue(int x, int y) {
        return logicalGrid[x][y];
    }

    public synchronized void shiftRemoveCompleted(List<Integer> completed) {
        for(int index : completed){
            for(int[] column : logicalGrid){
                ArrayList<Integer> columnAsList = new ArrayList<Integer>();
                for(int i = 0; i < column.length; i++){
                    columnAsList.add(column[i]);
                }
                columnAsList.remove(index);
                columnAsList.add(0, -1);
                int[] strippedColumn = new int[StaticGameEnvironment.VERTICAL_BLOCK_COUNT];
                for(int i = strippedColumn.length-1; i >= 0; i--){
                    strippedColumn[i] = columnAsList.get(i);
                }
                System.arraycopy(strippedColumn, 0, column, 0, StaticGameEnvironment.VERTICAL_BLOCK_COUNT);
            }
        }
    }
}
