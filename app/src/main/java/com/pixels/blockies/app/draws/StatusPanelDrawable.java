package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;
import com.pixels.blockies.app.draws.enums.Number;
import com.pixels.blockies.app.game.GameContext;
import com.pixels.blockies.app.game.figures.Rotatable;

public class StatusPanelDrawable implements Drawable {

    /**
     * Factor that the blocks will be shrinked to,
     * in comparison to the standard-block size
     */
    public static final int MINI_BLOCK_FACTOR = 2;

    /**
     * The whole screen width is used for the preview
     */
    private int width = -1;
    private int blockHeight = -1;
    private int blockWidth = -1;
    private int blockStroke = -1;

    private FigurePreview preview = new FigurePreview();
    private Score score = new Score();

    ViewContext context = null;

    public StatusPanelDrawable(){
        context = DrawingView.getViewContext();
        this.blockHeight = context.getBlockHeight()/MINI_BLOCK_FACTOR;
        this.blockWidth = context.getBlockWidth()/MINI_BLOCK_FACTOR;
        this.blockStroke = context.getThickness() / MINI_BLOCK_FACTOR;
        this.width = (int) (context.getWidth() - context.getBorder());
    }

    @Override
    public void draw(Canvas canvas) {
        preview.draw(canvas);
        score.draw(canvas);
    }

    class FigurePreview implements Drawable {
        @Override
        public void draw(Canvas canvas) {
            Rotatable f = GameContext.PICKER.peek();
            int[][] model = f.get();
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if (model[i][j] != -1) {
                        int blockY = (i * blockHeight) + context.getBorder();
                        int blockX = (j * blockWidth) + width - (model[i].length * blockWidth);
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
                        int blockY = (i * (blockHeight / adjustment)) + context.getBorder();
                        int blockX = (j * (blockWidth / adjustment)) + context.getBorder() + startX;
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
