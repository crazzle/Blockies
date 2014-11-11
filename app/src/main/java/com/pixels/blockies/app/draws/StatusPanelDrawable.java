package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.figures.Rotatable;

/**
 * Created by mark on 11.11.14.
 */
public class StatusPanelDrawable implements Drawable {

    Paint paint = new Paint();
    private int x = StaticGameEnvironment.BORDER-10;
    private int y = StaticGameEnvironment.BORDER-10;
    FigurePreview preview = new FigurePreview();

    @Override
    public void draw(Canvas canvas) {
        preview.draw(canvas);
    }

    class FigurePreview implements Drawable {
        int blockWidth = 50;
        int blockHeight = 50;

        @Override
        public void draw(Canvas canvas) {
            Rotatable f = StaticGameEnvironment.picker.peek();
            int[][] model = f.get();
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if(model[i][j] != -1) {
                        int blockX = x + (i * blockWidth) + StaticGameEnvironment.BORDER;
                        int blockY = y + (j * blockHeight) + StaticGameEnvironment.BORDER;
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
                paint.setStrokeWidth(6);
                paint.setStyle(Paint.Style.STROKE);
                paint.setColor(GameColor.WHITE.getColor());
                canvas.drawRect(x, y, x + blockWidth, y + blockHeight, paint);
            }

            public void setSpecificColor(int specificColor) {
                this.specificColor = specificColor;
            }
        }

    }
}
