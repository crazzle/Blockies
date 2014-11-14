package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;

class BlockDrawable implements Drawable {
    private Paint paint = new Paint();
    private int x = 0;
    private int y = 0;
    private int specificColor = -1;
    private int specificBlockStroke = -1;
    private int adjustment = -1;
    private int blockWidth = -1;
    private int blockHeight = -1;

    public BlockDrawable(int x, int y, int blockwidth, int blockHeight){
        this.x = x;
        this.y = y;
        this.adjustment = 1;
        this.blockWidth = blockwidth;
        this.blockHeight = blockHeight;
    }

    public BlockDrawable(int x, int y, int blockwidth, int blockHeight, int adjustment){
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
