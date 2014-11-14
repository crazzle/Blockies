package com.pixels.blockies.app.draws;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.pixels.blockies.app.draws.enums.GameColor;
import com.pixels.blockies.app.game.GameContext;
import com.pixels.blockies.app.game.BlockMover;

public class DrawingView extends View implements View.OnTouchListener {

    float baseWidth = 1080;
    float baseHeight = 1920;
    int baseStrokeThickness = 10;
    int baseBorder = 25;
    static int border = -1;

    int thickness = -1;
    float width = -1;
    float height = -1;
    boolean isInit = false;
    float histX = width / 2;
    float histY = height / 2;
    GridDrawable grid = GridDrawable.getInstance();
    StatusPanelDrawable statusPanel = new StatusPanelDrawable();
    RestartScreenDrawable restart = new RestartScreenDrawable();
    private BlockMover mover;
    int step = -1;

    GestureDetector gestureDetector = new GestureDetector(this.getContext(), new TapListener());

    private class TapListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            if(!mover.hasEnded()){
                mover.rotate();
            }else{
                mover.restart();
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    /**
     * Constructor
     *
     * @param context
     */
    public DrawingView(Context context) {
        super(context);
        this.setBackgroundColor(GameColor.BLUE.getColor());
    }

    /**
     * Initialize
     */
    public void init() {
        if (!isInit) {
            float factor = ((width/baseWidth)+(height/baseHeight))/2;
            thickness = (int) (factor*baseStrokeThickness);
            border = (int) (factor*baseBorder);
            int blockHeight = (int) (height - 2 * border) / GameContext.VERTICAL_BLOCK_COUNT;
            int blockWidth =  (int) (width - 2 * border) / GameContext.HORIZONTAL_BLOCK_COUNT;
            initializeGrid(blockHeight, blockWidth);
            statusPanel.setStrokeThickness(thickness);
            statusPanel.init(blockHeight, blockWidth, (int) width);
            restart.setStrokeThickness(thickness);
            restart.init(blockHeight, blockWidth, (int) width, (int) height);
            step = (int) width / GameContext.HORIZONTAL_BLOCK_COUNT;
            isInit = true;
        }
        this.setOnTouchListener(this);
    }

    /**
     * Initialize grid
     *
     * @param blockHeight
     * @param blockWidth
     */
    private void initializeGrid(int blockHeight, int blockWidth) {
        grid.setThickness(thickness);
        grid.setCellHeight(blockHeight);
        grid.setCellWidth(blockWidth);
    }

    /**
     * Draw
     *
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        statusPanel.draw(canvas);
        if(!mover.hasEnded()) {
            grid.draw(canvas);
        }else{
            restart.draw(canvas);
        }
        invalidate();
    }

    /**
     * Get device resolution to draw perfect layout
     *
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
     *
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (isInit && mover != null) {
            this.gestureDetector.onTouchEvent(motionEvent);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                histX = motionEvent.getX();
                histY = motionEvent.getY();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                moveHorizontal(motionEvent, step);
                moveVertical(motionEvent, step);
            }
        }
        return true;
    }

    private void moveVertical(MotionEvent motionEvent, int step) {
        float y = motionEvent.getY();
        float deltaY = y - histY;
        if (Math.abs(deltaY) > step / 1.5) {
            histY = y;
            if (deltaY > 0) {
                mover.moveBlockDown();
            }
        }
    }

    private void moveHorizontal(MotionEvent motionEvent, int step) {
        float x = motionEvent.getX();
        float deltaX = x - histX;
        if (Math.abs(deltaX) > step/1.25) {
            histX = x;
            int direction = deltaX < 0 ? -1 : 1;
            mover.moveHorizontalPosition(direction);
        }
    }

    public void setBlockMover(BlockMover mover) {
        this.mover = mover;
    }

    public static int getBorder(){
        return border;
    }

}
