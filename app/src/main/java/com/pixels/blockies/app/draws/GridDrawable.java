package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.Grid;

/**
 * Created by keinhoerster on 3/17/14.
 */
public class GridDrawable implements Drawable {
    /**
     * Define Layout-Values
     */
    public static final int STROKE = 3;
    /**
     * Singleton instance
     */
    private static GridDrawable grid = null;
    int cellHeight = -1;
    int cellWidth = -1;

    /**
     * Drawing related
     */
    BlockDrawable[][] blockDrawables = new BlockDrawable[StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT][StaticGameEnvironment.VERTICAL_BLOCK_COUNT];
    Grid logicalGrid = Grid.getInstance();
    private int thickness;

    private GridDrawable() {

    }

    public static GridDrawable getInstance() {
        if (grid == null) {
            grid = new GridDrawable();
        }
        return grid;
    }

    @Override
    public void draw(Canvas canvas) {
        updateFromLogicalGrid();
        for (int i = 0; i < blockDrawables.length; i++) {
            for (int j = 0; j < blockDrawables[i].length; j++) {
                if (blockDrawables[i][j] != null) {
                    blockDrawables[i][j].draw(canvas);
                }
            }
        }
    }

    private void updateFromLogicalGrid() {
        for (int i = 0; i < blockDrawables.length; i++) {
            BlockDrawable[] line = blockDrawables[i];
            for (int j = 0; j < line.length; j++) {
                int color = logicalGrid.getPositionValue(i, j);
                if (color > -1) {
                    int blockColor = GameColor.forFigureNumber(color);
                    BlockDrawable b = new BlockDrawable(cellWidth, cellHeight, blockColor);
                    b.setX((i * cellWidth) + StaticGameEnvironment.BORDER);
                    b.setY((j * cellHeight) + StaticGameEnvironment.BORDER);
                    b.setThickness(thickness);
                    line[j] = b;
                } else {
                    line[j] = null;
                }
            }
        }
    }

    public void setCellHeight(int cellHeight) {
        this.cellHeight = cellHeight;
    }

    public void setCellWidth(int cellWidth) {
        this.cellWidth = cellWidth;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
}
