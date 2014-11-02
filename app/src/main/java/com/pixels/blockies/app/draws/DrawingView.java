package com.pixels.blockies.app.draws;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
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
    float histX = width/2;
    float histY = height/2;

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
        int index = motionEvent.getActionIndex();
        int action = motionEvent.getActionMasked();
        int pointerId = motionEvent.getPointerId(index);

        if (isInit && mover != null) {
            int step = width/StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT;
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                histX = width/2;
                histY = height/2;
            }
            if(motionEvent.getAction() == MotionEvent.ACTION_MOVE){
                float x = motionEvent.getX();
                float deltaX = x-histX;
                if(Math.abs(deltaX) > step/1.5) {
                    histX = x;
                    int direction = deltaX < 0 ? -1 : 1;
                    mover.moveHorizontalPosition(direction);
                }

                float y = motionEvent.getY();
                float deltaY = y - histY;
                if(Math.abs(deltaY) > step/2){
                    histY = y;
                    if(deltaY > 0) {
                        mover.moveBlockDown();
                    }
                }
            }
        }
        return true;
    }

    public void setBlockMover(BlockMover mover) {
        this.mover = mover;
    }

}
