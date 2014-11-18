package com.pixels.blockies.app.game;

import java.util.ArrayList;
import java.util.List;

/**
 * The logical grid behaves as the gameboard that contains the blocks
 */
public class Grid {
    /**
     * private instance providing singleton access
     */
    private static Grid grid = null;

    /**
     * The actual grid
     */
    private int[][] logicalGrid = new int[GameContext.HORIZONTAL_BLOCK_COUNT][GameContext.VERTICAL_BLOCK_COUNT];

    /**
     * Private constructor limiting the instances
     */
    private Grid() {
        initLogicalGrid();
    }

    /**
     * indicator for an empty field
     */
    public static final int EMPTY = -1;

    /**
     * Factory method that returns the instance
     */
    public static Grid getInstance() {
        if (grid == null) {
            grid = new Grid();
        }
        return grid;
    }

    /**
     * Initializes the logical grid with -1
     * (-1 means the field is empty)
     */
    public synchronized void initLogicalGrid() {
        for (int i = 0; i < logicalGrid.length; i++) {
            for (int j = 0; j < logicalGrid[i].length; j++) {
                logicalGrid[i][j] = EMPTY;
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

    /**
     * Removes the completed lines and shifts all lines above down
     */
    public synchronized void shiftRemoveCompleted(List<Integer> completed) {
        for(int index : completed){
            for(int[] column : logicalGrid){
                ArrayList<Integer> columnAsList = new ArrayList<Integer>();
                for(int i = 0; i < column.length; i++){
                    columnAsList.add(column[i]);
                }
                columnAsList.remove(index);
                columnAsList.add(0, -1);
                int[] strippedColumn = new int[GameContext.VERTICAL_BLOCK_COUNT];
                for(int i = strippedColumn.length-1; i >= 0; i--){
                    strippedColumn[i] = columnAsList.get(i);
                }
                System.arraycopy(strippedColumn, 0, column, 0, GameContext.VERTICAL_BLOCK_COUNT);
            }
        }
    }
}
