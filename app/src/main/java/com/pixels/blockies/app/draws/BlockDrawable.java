package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by keinmark on 08.03.14.
 */
public class BlockDrawable implements Drawable {
    Paint paint = new Paint();
    private int width = 0;
    private int height = 0;
    private int x = -1;
    private int y = -1;
    private int specificColor = -1;
    private int thickness;

    public BlockDrawable(int width, int height, int specificColor) {
        this.width = width;
        this.height = height;
        this.specificColor = specificColor;
    }

    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(specificColor);
        canvas.drawRect(x, y, x + width, y + height, paint);
        drawStroke(canvas);
    }

    public void drawStroke(Canvas canvas){
        paint.setStrokeWidth(thickness);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(GameColor.WHITE.getColor());
        canvas.drawRect(x, y, x + width, y + height, paint);
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setThickness(int thickness) {
        this.thickness = thickness;
    }
}
