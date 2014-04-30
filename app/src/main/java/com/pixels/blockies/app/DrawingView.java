package com.pixels.blockies.app;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.pixels.blockies.app.draws.GridDrawable;
import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.BlockMover;

/**
 * Created by keinmark on 08.03.14.
 */
public class DrawingView extends View implements View.OnTouchListener {

    /**
     * Technical Variables
     */
    int width = -1;
    int height = -1;
    boolean isInit = false;
    GestureDetectorCompat mDetector = new GestureDetectorCompat(this.getContext(), new FlingDownListener());

    /**
     * The needed Grid
     */
    GridDrawable grid = GridDrawable.getInstance();
    private BlockMover mover;

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
            int blockHeight = (height - 2 * StaticGameEnvironment.BORDER) / StaticGameEnvironment.VERTICAL_BLOCK_COUNT;
            int blockWidth = (width - 2 * StaticGameEnvironment.BORDER) / StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT;
            initializeGrid(blockHeight, blockWidth);
            isInit = true;
        }
        this.setOnTouchListener(this);
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

    /**
     * Recognize block movement by touch
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isInit && mover != null) {
            int step = width/StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT;
            int newXGridCoordinate = (int) motionEvent.getX()/step;
            if(newXGridCoordinate > 9){
                newXGridCoordinate = 9;
            }
            mover.moveHorizontalPosition(newXGridCoordinate);
        }
        this.mDetector.onTouchEvent(motionEvent);
        return true;
    }

    public void setBlockMover(BlockMover mover) {
        this.mover = mover;
    }

    /**
     * Listener to recognize fling down
     */
    class FlingDownListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 100;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE) {
                mover.moveToBottom();
            }
            return false;
        }
    }
}
