package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.figures.Rotatable;

/**
 * Created by mark on 11.11.14.
 */
public class RestartScreenDrawable implements Drawable {

    public static final float MINI_BLOCK_FACTOR = 1.5f;

    private Paint paint = new Paint();
    private int xCenter = -1;
    private int yCenter = -1;
    private int blockHeight = -1;
    private int blockWidth = -1;
    private int blockStroke = -1;
    private int strokeThickness = 10;

    private String TAP = "TAP";


    @Override
    public void draw(Canvas canvas) {
        int next = 0;
        for(char c : TAP.toCharArray()){
            drawChar(canvas, c, xCenter+next, yCenter);
            next+=(4*blockWidth);
        }
    }

    public void drawChar(Canvas canvas, char letter, int xPos, int yPos){
        boolean[][] model = Letter.forLetter(letter).getLetter();
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                if (model[i][j]) {
                    int blockY = (i * blockHeight) + yPos;
                    int blockX = (j * blockWidth) + xPos;
                    AdjustableMiniBlock b = new AdjustableMiniBlock(blockX, blockY);
                    b.setSpecificColor(GameColor.RED.getColor());
                    b.setSpecificBlockStroke(blockStroke);
                    b.draw(canvas);
                }
            }
        }
    }

    public void init(int blockHeight, int blockWidth, int maxWidth, int maxHeight){
        this.blockHeight = (int) (blockHeight/MINI_BLOCK_FACTOR);
        this.blockWidth = (int) (blockWidth/MINI_BLOCK_FACTOR);
        this.blockStroke = (int) (strokeThickness / MINI_BLOCK_FACTOR);
        this.xCenter = (maxWidth/2)-3*2*this.blockWidth;
        this.yCenter = (maxHeight/2)-3*this.blockHeight;
    }

    public void setStrokeThickness(int thickness){
        this.strokeThickness = thickness;
    }

    class AdjustableMiniBlock implements Drawable {
        private int x = 0;
        private int y = 0;
        private int specificColor = -1;
        private int specificBlockStroke = -1;
        private int adjustment = -1;

        public AdjustableMiniBlock(int x, int y){
            this.x = x;
            this.y = y;
            this.adjustment = 1;
        }

        public AdjustableMiniBlock(int x, int y, int adjustment){
            this.x = x;
            this.y = y;
            this.adjustment = adjustment;
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
}
