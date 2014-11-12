package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import android.graphics.Paint;
import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.GameInformation;
import com.pixels.blockies.app.game.figures.Rotatable;

/**
 * Created by mark on 11.11.14.
 */
public class StatusPanelDrawable implements Drawable {

    public static final int MINI_BLOCK_FACTOR = 2;
    public static final int STROKE_THICKNESS = 10;

    private Paint paint = new Paint();
    private FigurePreview preview = new FigurePreview();
    private Score score = new Score();

    private int xEnd = -1;
    private int blockHeight = -1;
    private int blockWidth = -1;
    private int blockStroke = -1;
    private int maxWidth = -1;


    @Override
    public void draw(Canvas canvas) {
        preview.draw(canvas);
        score.draw(canvas);
    }

    public void init(int blockHeight, int blockWidth, int maxWidth){
        this.blockHeight = blockHeight/MINI_BLOCK_FACTOR;
        this.blockWidth = blockWidth/MINI_BLOCK_FACTOR;
        this.blockStroke = STROKE_THICKNESS / MINI_BLOCK_FACTOR;
        this.maxWidth = maxWidth;
        this.xEnd = (maxWidth-StaticGameEnvironment.BORDER);
    }

    class FigurePreview implements Drawable {
        @Override
        public void draw(Canvas canvas) {
            Rotatable f = StaticGameEnvironment.picker.peek();
            int[][] model = f.get();
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if (model[i][j] != -1) {
                        int blockY = (i * blockHeight) + StaticGameEnvironment.BORDER;
                        int blockX = (j * blockWidth) + xEnd - (model[i].length * blockWidth);
                        AdjustableMiniBlock b = new AdjustableMiniBlock(blockX, blockY);
                        b.setSpecificColor(GameColor.forFigureNumber(model[i][j]));
                        b.setSpecificBlockStroke(blockStroke);
                        b.draw(canvas);
                    }
                }
            }
        }
    }


    class Score implements Drawable {
        public void draw(Canvas canvas) {
            int score = GameInformation.getScore();

            int oneth = score%10;

            score /= 10;
            int tenth = score%10;

            score /= 10;
            int hundredth = score;

            int gap = 0;

            if(hundredth > 0) {
                boolean[][] modelHundreth = Number.forNumber(hundredth).getNumber();
                drawModel(canvas, modelHundreth, gap);
                gap += (Number.COLUMN_COUNT + 1) * blockWidth/2;
            }

            if(tenth > 0) {
                boolean[][] modelTenth = Number.forNumber(tenth).getNumber();
                drawModel(canvas, modelTenth, gap);
                gap += (Number.COLUMN_COUNT + 1) * blockWidth/2;
            }

            boolean[][] modelOneth = Number.forNumber(oneth).getNumber();
            drawModel(canvas, modelOneth, gap);
        }

        private void drawModel(Canvas canvas, boolean[][] model, int startX){
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if(model[i][j]) {
                        int blockY = (i * (blockHeight / 2)) + StaticGameEnvironment.BORDER;
                        int blockX = (j * (blockWidth / 2)) + StaticGameEnvironment.BORDER + startX;
                        AdjustableMiniBlock b = new AdjustableMiniBlock(blockX,blockY, 2);
                        b.setSpecificColor(GameColor.ORANGE.getColor());
                        b.setSpecificBlockStroke(blockStroke/2);
                        b.draw(canvas);
                    }
                }
            }
        }
    }

    class AdjustableMiniBlock implements Drawable {
        private int x = 0;
        private int y = 0;
        private int specificColor = -1;
        private int specificBlockStroke = -1;
        private float adjustment = -1;

        public AdjustableMiniBlock(int x, int y){
            this.x = x;
            this.y = y;
            this.adjustment = 1;
        }

        public AdjustableMiniBlock(int x, int y, float adjustment){
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
