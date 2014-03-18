package com.pixels.blockies.app;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.pixels.blockies.app.draws.GridDrawable;
import com.pixels.blockies.app.environment.StaticGameEnvironment;

/**
 * Created by keinmark on 08.03.14.
 */
public class DrawingView extends View {

    /**
     * Technical Variables
     */
    int width = -1;
    int height = -1;
    boolean isInit = false;

    /**
     * The needed Grid
     */
    GridDrawable grid = GridDrawable.getInstance();

    /**
     * Constructor
     * @param context
     */
    public DrawingView(Context context) {
        super(context);
    }

    /**
     * Initialize
     */
    public void init() {
        if (!isInit) {
            int blockHeight = (height - 2 * StaticGameEnvironment.BORDER) / StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT;
            int blockWidth = (width - 2 * StaticGameEnvironment.BORDER) / StaticGameEnvironment.VERTICAL_BLOCK_COUNT;
            initializeGrid(blockHeight, blockWidth);
            isInit = true;
        }
    }

    /**
     * Initialize grid
     * @param blockHeight
     * @param blockWidth
     */
    private void initializeGrid(int blockHeight, int blockWidth) {
        grid.setCellHeight(blockHeight);
        grid.setCellWidth(blockWidth);
    }

    /**
     * Draw
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        grid.draw(canvas);
        invalidate();
    }

    /**
     * Get device resolution to draw perfect layout
     * @param xNew
     * @param yNew
     * @param xOld
     * @param yOld
     */
    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        width = xNew;
        height = yNew;
    }

}
