package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.GameInformation;
import com.pixels.blockies.app.game.figures.Rotatable;

/**
 * Created by mark on 11.11.14.
 */
public class StatusPanelDrawable implements Drawable {

    public static int MINI_BLOCK_FACTOR = 2;
    public static int STROKE_THICKNESS = 10;

    Paint paint = new Paint();
    private int x = StaticGameEnvironment.BORDER;
    private int y = StaticGameEnvironment.BORDER;
    int blockHeight = -1;
    int blockWidth = -1;
    int blockStroke = -1;
    int maxWidth = -1;
    FigurePreview preview = new FigurePreview();
    PauseButton pause = new PauseButton();
    Score score = new Score();

    @Override
    public void draw(Canvas canvas) {
        preview.draw(canvas);
        pause.draw(canvas);
        score.draw(canvas);
    }

    public void init(int blockHeight, int blockWidth, int maxWidth){
        this.blockHeight = blockHeight/MINI_BLOCK_FACTOR;
        this.blockWidth = blockWidth/MINI_BLOCK_FACTOR;
        this.blockStroke = STROKE_THICKNESS / MINI_BLOCK_FACTOR;
        this.maxWidth = maxWidth;
    }

    class FigurePreview implements Drawable {

        @Override
        public void draw(Canvas canvas) {
            Rotatable f = StaticGameEnvironment.picker.peek();
            int[][] model = f.get();
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if(model[i][j] != -1) {
                        int blockY = (i * blockHeight) + StaticGameEnvironment.BORDER;
                        int blockX = (j * blockWidth) + StaticGameEnvironment.BORDER;
                        MiniBlock b = new MiniBlock(blockX,blockY);
                        b.setSpecificColor(GameColor.forFigureNumber(model[i][j]));
                        b.draw(canvas);
                    }
                }
            }
        }

        class MiniBlock implements Drawable {
            private int x = 0;
            private int y = 0;
            private int specificColor = -1;

            public MiniBlock(int x, int y){
                this.x = x;
                this.y = y;
            }

            public void draw(Canvas canvas) {
                paint.setStyle(Paint.Style.FILL);
                paint.setColor(specificColor);
                canvas.drawRect(x, y, x + blockWidth, y + blockHeight, paint);
                drawStroke(canvas);
            }

            public void drawStroke(Canvas canvas){
                paint.setStrokeWidth(blockStroke);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(GameColor.WHITE.getColor());
                canvas.drawRect(x, y, x + blockWidth, y + blockHeight, paint);
            }

            public void setSpecificColor(int specificColor) {
                this.specificColor = specificColor;
            }
        }
    }

    class PauseButton implements Drawable {
        public void draw(Canvas canvas) {
            int startX1 = maxWidth - StaticGameEnvironment.BORDER - 3 * blockWidth;
            int startY1 = y;
            int endX1 = maxWidth - StaticGameEnvironment.BORDER - 2 * blockWidth;
            int endY1 = startY1 + 2 * blockHeight;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(GameColor.GRAY.getColor());
            canvas.drawRect(startX1, startY1, endX1, endY1, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(GameColor.WHITE.getColor());
            canvas.drawRect(startX1, startY1, endX1, endY1, paint);

            int startX2 = maxWidth - StaticGameEnvironment.BORDER - 1 * blockWidth;
            int startY2 = y;
            int endX2 = maxWidth - StaticGameEnvironment.BORDER;
            int endY2 = startY2 + 2 * blockHeight;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(GameColor.GRAY.getColor());
            canvas.drawRect(startX2, startY2, endX2, endY2, paint);
            paint.setStyle(Paint.Style.STROKE);
            paint.setColor(GameColor.WHITE.getColor());
            canvas.drawRect(startX2, startY2, endX2, endY2, paint);
        }
    }

    class Score implements Drawable {
        public void draw(Canvas canvas) {
            int startX = (maxWidth/2);
            int startY = y + StaticGameEnvironment.BORDER + blockHeight;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(GameColor.BLACK.getColor());
            paint.setTextSize(2*blockHeight);
            paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            canvas.drawText(""+GameInformation.getScore(), startX, startY, paint);
        }
    }
}
