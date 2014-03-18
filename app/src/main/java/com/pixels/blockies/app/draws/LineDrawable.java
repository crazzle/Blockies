package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by keinmark on 08.03.14.
 */
public class LineDrawable implements Drawable{
    Paint paint = new Paint();
    private int x1 = -1;
    private int y1 = -1;
    private int x2 = -1;
    private int y2 = -1;

    public LineDrawable(int x1, int y1, int x2, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public void draw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(3);
        canvas.drawLine(x1, y1, x2, y2, paint);
    }

}
