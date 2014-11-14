package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;
import com.pixels.blockies.app.game.GameContext;
import com.pixels.blockies.app.game.Grid;

public class GridDrawable implements Drawable {
    /**
     * Singleton instance
     */
    private static GridDrawable grid = null;
    int cellHeight = -1;
    int cellWidth = -1;

    BlockDrawable[][] blockDrawables =
            new BlockDrawable[GameContext.HORIZONTAL_BLOCK_COUNT][GameContext.VERTICAL_BLOCK_COUNT];
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
                    int blockX = (i * cellWidth) + DrawingView.getBorder();
                    int blockY = (j * cellHeight) + DrawingView.getBorder();
                    BlockDrawable b = new BlockDrawable(blockX, blockY, cellWidth, cellHeight);
                    b.setSpecificColor(blockColor);
                    b.setSpecificBlockStroke(thickness);
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
