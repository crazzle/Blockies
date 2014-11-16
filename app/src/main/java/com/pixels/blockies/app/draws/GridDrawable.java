package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;
import com.pixels.blockies.app.game.GameContext;
import com.pixels.blockies.app.game.Grid;

/**
 * Draws the underlying logical grid of the game
 */
public class GridDrawable implements Drawable {

    /**
     * Provide the GridDrawable as a singleton
     */
    private static GridDrawable grid = null;

    /**
     * Array of block drawables which corresponds to the logical grid
     */
    BlockDrawable[][] blockDrawables = new BlockDrawable[GameContext.HORIZONTAL_BLOCK_COUNT][GameContext.VERTICAL_BLOCK_COUNT];

    /**
     * The logical grid which is used by the block-mover
     */
    Grid logicalGrid = Grid.getInstance();

    /**
     * The ViewContext with all the important information about sizes, resolution and much more
     */
    ViewContext context = null;

    /**
     * Private constructor for singleton access
     */
    private GridDrawable() {
       context = DrawingView.getViewContext();
    }

    /**
     * Factory method to provide the singleton instance
     *
     * @return
     */
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

    /**
     * Updates the array of block drawables with the current information from the local grid
     */
    private void updateFromLogicalGrid() {
        for (int i = 0; i < blockDrawables.length; i++) {
            BlockDrawable[] line = blockDrawables[i];
            for (int j = 0; j < line.length; j++) {
                int fieldValue = logicalGrid.getPositionValue(i, j);
                if (fieldValue > Grid.EMPTY) {
                    int blockColor = GameColor.forFigureNumber(fieldValue);
                    int blockX = (i * context.getBlockWidth()) + context.getBorder();
                    int blockY = (j * context.getBlockHeight()) + context.getBorder();
                    BlockDrawable b = new BlockDrawable(blockX, blockY, context.getBlockWidth(), context.getBlockHeight());
                    b.setSpecificColor(blockColor);
                    b.setSpecificBlockStroke(context.getThickness());
                    line[j] = b;
                } else {
                    line[j] = null;
                }
            }
        }
    }
}
