package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;

/**
 * Draws a single block on a specific location by given width and height.
 * It can also be adjusted (shrink/expand) by a factor. That way the proportions
 * to its stroke thickness will be kept.
 */
class BlockDrawable implements Drawable {

    private Paint paint = new Paint();

    /**
     * The cooridinates of the block
     */
    private int x = 0;
    private int y = 0;

    /**
     * The color of the block
     */
    private int specificColor = -1;

    /**
     * The stroke size of the block
     */
    private int specificBlockStroke = -1;

    /**
     * The adjustment to scale the block up or down
     */
    private float adjustment = -1;

    /**
     * Width and height of the block
     */
    private int blockWidth = -1;
    private int blockHeight = -1;

    /**
     * Blockdrawable that keeps its size as defined by blockwidth and blockheight
     *
     * @param x
     * @param y
     * @param blockwidth
     * @param blockHeight
     */
    public BlockDrawable(int x, int y, int blockwidth, int blockHeight){
        this.x = x;
        this.y = y;
        this.adjustment = 1;
        this.blockWidth = blockwidth;
        this.blockHeight = blockHeight;
    }

    /**
     * Blockdrawable that adjusts its size and stroke by a given factor
     *
     * @param x
     * @param y
     * @param blockwidth
     * @param blockHeight
     * @param adjustment
     */
    public BlockDrawable(int x, int y, int blockwidth, int blockHeight, float adjustment){
        this.x = x;
        this.y = y;
        this.adjustment = adjustment;
        this.blockWidth = blockwidth;
        this.blockHeight = blockHeight;
    }

    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(specificColor);
        canvas.drawRect(x, y, x + blockWidth / adjustment, y + blockHeight / adjustment, paint);
        drawStroke(canvas);
    }

    public void drawStroke(Canvas canvas){
        paint.setStrokeWidth(specificBlockStroke);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(GameColor.WHITE.getColor());
        canvas.drawRect(x, y, x + blockWidth/adjustment, y + blockHeight/adjustment, paint);
    }

    public void setSpecificColor(int specificColor) {
        this.specificColor = specificColor;
    }

    public void setSpecificBlockStroke(int specificBlockStroke) {
        this.specificBlockStroke = specificBlockStroke;
    }
}
