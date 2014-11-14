package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;
import com.pixels.blockies.app.draws.enums.Number;
import com.pixels.blockies.app.game.GameContext;
import com.pixels.blockies.app.game.figures.Rotatable;

public class StatusPanelDrawable implements Drawable {

    public static final int MINI_BLOCK_FACTOR = 2;

    private FigurePreview preview = new FigurePreview();
    private Score score = new Score();

    private int xEnd = -1;
    private int blockHeight = -1;
    private int blockWidth = -1;
    private int blockStroke = -1;
    private int strokeThickness = 10;


    @Override
    public void draw(Canvas canvas) {
        preview.draw(canvas);
        score.draw(canvas);
    }

    public void init(int blockHeight, int blockWidth, int maxWidth){
        this.blockHeight = blockHeight/MINI_BLOCK_FACTOR;
        this.blockWidth = blockWidth/MINI_BLOCK_FACTOR;
        this.blockStroke = strokeThickness / MINI_BLOCK_FACTOR;
        this.xEnd = (maxWidth - DrawingView.getBorder());
    }

    public void setStrokeThickness(int thickness){
        this.strokeThickness = thickness;
    }

    class FigurePreview implements Drawable {
        @Override
        public void draw(Canvas canvas) {
            Rotatable f = GameContext.PICKER.peek();
            int[][] model = f.get();
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if (model[i][j] != -1) {
                        int blockY = (i * blockHeight) + DrawingView.getBorder();
                        int blockX = (j * blockWidth) + xEnd - (model[i].length * blockWidth);
                        BlockDrawable b = new BlockDrawable(blockX, blockY, blockWidth, blockHeight);
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
            int score = GameContext.getScore();
            int adjustment = 2;

            int oneth = score%10;

            score /= 10;
            int tenth = score%10;

            score /= 10;
            int hundredth = score;

            int gap = 0;

            if(hundredth > 0) {
                boolean[][] modelHundreth = com.pixels.blockies.app.draws.enums.Number.forNumber(hundredth).getNumber();
                drawModel(canvas, modelHundreth, gap, adjustment);
                gap += (Number.COLUMN_COUNT + 1) * blockWidth/adjustment;
            }

            if(tenth > 0) {
                boolean[][] modelTenth = Number.forNumber(tenth).getNumber();
                drawModel(canvas, modelTenth, gap, adjustment);
                gap += (Number.COLUMN_COUNT + 1) * blockWidth/adjustment;
            }

            boolean[][] modelOneth = Number.forNumber(oneth).getNumber();
            drawModel(canvas, modelOneth, gap, adjustment);
        }

        private void drawModel(Canvas canvas, boolean[][] model, int startX, int adjustment){
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if(model[i][j]) {
                        int blockY = (i * (blockHeight / adjustment)) + DrawingView.getBorder();
                        int blockX = (j * (blockWidth / adjustment)) + DrawingView.getBorder() + startX;
                        BlockDrawable b = new BlockDrawable(blockX, blockY, blockWidth, blockHeight, adjustment);
                        b.setSpecificColor(GameColor.ORANGE.getColor());
                        b.setSpecificBlockStroke(blockStroke/adjustment);
                        b.draw(canvas);
                    }
                }
            }
        }
    }
}
