package com.pixels.blockies.game.draws;

import android.content.Context;
import android.graphics.Canvas;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import com.pixels.blockies.game.draws.enums.GameColor;
import com.pixels.blockies.game.game.GameContext;
import com.pixels.blockies.game.game.BlockMover;

/**
 * The drawing view visualizes the current gamestate.
 * Furthermore it recognizes user-actions and sends them
 * to the game-logic to be processed.
 */
public class DrawingView extends View implements View.OnTouchListener {

    /**
     * To ensure the same proportion between border width and
     * block size a 1080p resolution is defined. Different
     * resolutions will be down-/upscaled to ensure the same
     * ratio
     */
    float baseWidth = 1080;
    float baseHeight = 1920;

    /**
     * Ensure a strocke-thickness of 10 pixels on a
     * 1080p device
     */
    int baseStrokeThickness = 10;

    /**
     * Ensure a border of 25 pixels on a
     * 1080p device
     */
    int baseBorder = 25;

    /**
     * The ViewContext with common information
     * about the resolution/sizes/borders
     */
    private static ViewContext viewContext;

    /**
     * The maximum width of the device
     */
    private float width = -1;

    /**
     * The maximum height of the device
     */
    private float height = -1;

    /**
     * The x-coordinate when the finger starts
     * touching the screen
     */
    private float histX = -1;

    /**
     * The y-coordinate when the finger starts
     * touching the screen
     */
    private float histY = -1;

    /**
     * Drawable that draws the grid according
     * to its content
     */
    private GridDrawable grid = null;

    /**
     * Drawable that draws the Status-Panel
     */
    private StatusPanelDrawable statusPanel = null;

    /**
     * Drawable that draws the Restart-Screen
     */
    private RestartScreenDrawable restart = null;

    /**
     * The BlockMover acts as the overall game logic.
     * All inputs are translated to specific commands
     * for the blockmover in order to react to the inputs
     */
    private BlockMover mover;

    /**
     * Recognizes taps to rotate the current figure
     */
    private GestureDetector gestureDetector = new GestureDetector(this.getContext(), new TapListener());

    /**
     * Listener for the gesture detector that recognizes taps
     */
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
     * Initializes the drawing view
     * Important: viewContext has to be build before
     * other drawables get instantiated
     */
    public void init() {
        if (viewContext == null) {
            buildViewContext();
            grid = GridDrawable.getInstance();
            statusPanel = new StatusPanelDrawable();
            restart = new RestartScreenDrawable();
            this.setOnTouchListener(this);
        }
    }

    private void buildViewContext() {
        float factor = ((width/baseWidth)+(height/baseHeight))/2;
        viewContext = new ViewContext();
        int border = (int) (factor*baseBorder);
        viewContext.setBorder(border);
        int strokeThickness = (int) (factor*baseStrokeThickness);
        viewContext.setStrokeThickness(strokeThickness);
        viewContext.setWidth(width);
        viewContext.setHeight(height);
        int blockHeight = (int) (height - 2 * border) / GameContext.VERTICAL_BLOCK_COUNT;
        int blockWidth =  (int) (width - 2 * border) / GameContext.HORIZONTAL_BLOCK_COUNT;
        viewContext.setBlockHeight(blockHeight);
        viewContext.setBlockWidth(blockWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        statusPanel.draw(canvas);
        if (!mover.hasEnded()) {
            grid.draw(canvas);
        } else {
            restart.draw(canvas);
        }
        invalidate();
    }

    /**
     * Get device resolution
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
        if (viewContext != null && mover != null) {
            this.gestureDetector.onTouchEvent(motionEvent);
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                histX = motionEvent.getX();
                histY = motionEvent.getY();
            } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                int step = (int) width / GameContext.HORIZONTAL_BLOCK_COUNT;
                moveHorizontal(motionEvent, step);
                moveVertical(motionEvent, step);
            }
        }
        return true;
    }

    private void moveVertical(MotionEvent motionEvent, int step) {
        float y = motionEvent.getY();
        float deltaY = y - histY;
        if (Math.abs(deltaY) > step / 2) {
            histY = y;
            if (deltaY > 0) {
                mover.moveBlockDown();
            }
        }
    }

    private void moveHorizontal(MotionEvent motionEvent, int step) {
        float x = motionEvent.getX();
        float deltaX = x - histX;
        if (Math.abs(deltaX) > step/1.5) {
            histX = x;
            int direction = deltaX < 0 ? -1 : 1;
            mover.moveHorizontalPosition(direction);
        }
    }

    public void setBlockMover(BlockMover mover) {
        this.mover = mover;
    }

    public static ViewContext getViewContext(){
        return viewContext;
    }

}
