package com.pixels.blockies.game.draws

import android.content.Context
import android.graphics.Canvas
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import com.pixels.blockies.game.draws.enums.GameColor
import com.pixels.blockies.game.game.BlockMover
import com.pixels.blockies.game.game.GameContext

/**
 * The drawing view visualizes the current gamestate.
 * Furthermore it recognizes user-actions and sends them
 * to the game-logic to be processed.
 */
class DrawingView(context: Context?) : View(context), OnTouchListener {
    /**
     * To ensure the same proportion between border width and
     * block size a 1080p resolution is defined. Different
     * resolutions will be down-/upscaled to ensure the same
     * ratio
     */
    var baseWidth = 1080f
    var baseHeight = 1920f

    /**
     * Ensure a strocke-thickness of 10 pixels on a
     * 1080p device
     */
    var baseStrokeThickness = 10

    /**
     * Ensure a border of 25 pixels on a
     * 1080p device
     */
    var baseBorder = 25

    /**
     * The maximum width of the device
     */
    private var width = -1f

    /**
     * The maximum height of the device
     */
    private var height = -1f

    /**
     * The x-coordinate when the finger starts
     * touching the screen
     */
    private var histX = -1f

    /**
     * The y-coordinate when the finger starts
     * touching the screen
     */
    private var histY = -1f

    /**
     * Drawable that draws the grid according
     * to its content
     */
    private var grid: GridDrawable? = null

    /**
     * Drawable that draws the Status-Panel
     */
    private var statusPanel: StatusPanelDrawable? = null

    /**
     * Drawable that draws the Restart-Screen
     */
    private var restart: RestartScreenDrawable? = null

    /**
     * The BlockMover acts as the overall game logic.
     * All inputs are translated to specific commands
     * for the blockmover in order to react to the inputs
     */
    private var mover: BlockMover? = null

    /**
     * Recognizes taps to rotate the current figure
     */
    private val gestureDetector = GestureDetector(context, TapListener())

    /**
     * Listener for the gesture detector that recognizes taps
     */
    private inner class TapListener : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent) {}
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            if (!mover!!.hasEnded()) {
                mover!!.rotate()
            } else {
                mover!!.restart()
            }
            return true
        }

        override fun onScroll(
            e1: MotionEvent,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent) {}
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return false
        }
    }

    /**
     * Constructor
     *
     * @param context
     */
    init {
        setBackgroundColor(GameColor.BLUE.getColor())
    }

    /**
     * Initializes the drawing view
     * Important: viewContext has to be build before
     * other drawables get instantiated
     */
    fun init() {
        if (viewContext == null) {
            buildViewContext()
            grid = GridDrawable.Companion.getInstance()
            statusPanel = StatusPanelDrawable()
            restart = RestartScreenDrawable()
            setOnTouchListener(this)
        }
    }

    private fun buildViewContext() {
        val factor = width / baseWidth + height / baseHeight / 2
        viewContext = ViewContext()
        val border = (factor * baseBorder).toInt()
        viewContext!!.setBorder(border)
        val strokeThickness = (factor * baseStrokeThickness).toInt()
        viewContext!!.setStrokeThickness(strokeThickness)
        viewContext!!.setWidth(width)
        viewContext!!.setHeight(height)
        val blockHeight = (height - 2 * border).toInt() / GameContext.VERTICAL_BLOCK_COUNT
        val blockWidth = (width - 2 * border).toInt() / GameContext.HORIZONTAL_BLOCK_COUNT
        viewContext!!.setBlockHeight(blockHeight)
        viewContext!!.setBlockWidth(blockWidth)
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        init()
        statusPanel!!.draw(canvas)
        if (!mover!!.hasEnded()) {
            grid!!.draw(canvas)
        } else {
            restart!!.draw(canvas)
        }
        invalidate()
    }

    /**
     * Get device resolution
     *
     * @param xNew
     * @param yNew
     * @param xOld
     * @param yOld
     */
    override fun onSizeChanged(xNew: Int, yNew: Int, xOld: Int, yOld: Int) {
        super.onSizeChanged(xNew, yNew, xOld, yOld)
        width = xNew.toFloat()
        height = yNew.toFloat()
    }

    /**
     * Recognize block movement by touch
     *
     * @param view
     * @param motionEvent
     * @return
     */
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        if (viewContext != null && mover != null) {
            gestureDetector.onTouchEvent(motionEvent)
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                histX = motionEvent.x
                histY = motionEvent.y
            } else if (motionEvent.action == MotionEvent.ACTION_MOVE) {
                val step = width.toInt() / GameContext.HORIZONTAL_BLOCK_COUNT
                moveHorizontal(motionEvent, step)
                moveVertical(motionEvent, step)
            }
        }
        return true
    }

    private fun moveVertical(motionEvent: MotionEvent, step: Int) {
        val y = motionEvent.y
        val deltaY = y - histY
        if (Math.abs(deltaY) > step / 2) {
            histY = y
            if (deltaY > 0) {
                mover!!.moveBlockDown()
            }
        }
    }

    private fun moveHorizontal(motionEvent: MotionEvent, step: Int) {
        val x = motionEvent.x
        val deltaX = x - histX
        if (Math.abs(deltaX) > step / 1.5) {
            histX = x
            val direction = if (deltaX < 0) -1 else 1
            mover!!.moveHorizontalPosition(direction)
        }
    }

    fun setBlockMover(mover: BlockMover?) {
        this.mover = mover
    }

    companion object {
        /**
         * The ViewContext with common information
         * about the resolution/sizes/borders
         */
        private var viewContext: ViewContext? = null
        fun getViewContext(): ViewContext? {
            return viewContext
        }
    }
}