package com.pixels.blockies.game.draws;

import android.graphics.Canvas;
import com.pixels.blockies.game.draws.api.Drawable;
import com.pixels.blockies.game.draws.enums.GameColor;
import com.pixels.blockies.game.draws.enums.Letter;
import com.pixels.blockies.game.draws.enums.Number;
import com.pixels.blockies.game.game.GameContext;
import com.pixels.blockies.game.game.Grid;
import com.pixels.blockies.game.game.figures.Rotatable;

/**
 * Draws the status panel on the top of the screen
 */
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

    /**
     * Preview and Score that are drawn on top
     */
    private FigurePreview preview = new FigurePreview();
    private Score score = new Score();

    /**
     * ViewContext with a lot of useful information
     */
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

    /**
     * Inner class that draws the preview on the top right corner
     */
    class FigurePreview implements Drawable {
        @Override
        public void draw(Canvas canvas) {
            // The the next rotatable that should be drawn
            Rotatable f = GameContext.PICKER.peek();
            int[][] model = f.get();
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if (model[i][j] != Grid.EMPTY) {
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

    /**
     * Inner class representing the score on the top left corner.
     * The score can reach a maximum of 999 lines, after that it starts
     * back at 0
     */
    class Score implements Drawable {
        public void draw(Canvas canvas) {
            // Get the current score from GameContext
            int score = GameContext.getScore();

            // Factor to adjust the blocks (shrink)
            int adjustment = 2;

            // get the lowest decimal digit of the score
            int oneth = score%10;

            // get the middle decimal digit of the score
            score /= 10;
            int tenth = score%10;

            // get the highest decimal digit of the score
            score /= 10;
            int hundredth = score;

            // The gap between the digits
            int gap = 0;

            if(hundredth > 0) {
                boolean[][] modelHundreth = Number.forNumber(hundredth).getNumber();
                drawModel(canvas, modelHundreth, gap, adjustment);
                gap += (com.pixels.blockies.game.draws.enums.Number.COLUMN_COUNT + 1) * blockWidth/adjustment;
            }

            if(tenth > 0) {
                boolean[][] modelTenth = Number.forNumber(tenth).getNumber();
                drawModel(canvas, modelTenth, gap, adjustment);
                gap += (Number.COLUMN_COUNT + 1) * blockWidth/adjustment;
            }

            boolean[][] modelOneth = Number.forNumber(oneth).getNumber();
            drawModel(canvas, modelOneth, gap, adjustment);
            gap += (Number.COLUMN_COUNT + 1) * blockWidth/adjustment;

            // slash
            boolean[][] modelSlash = Letter.forLetter('-').getLetter();
            drawModel(canvas, modelSlash, gap, adjustment);
            gap += (Letter.COLUMN_COUNT + 1) * blockWidth/adjustment;

            if(GameContext.HIGH_SCORE != null) {
                // Get the current highscore from GameContext
                score = GameContext.HIGH_SCORE.getScore();

                // get the lowest decimal digit of the score
                oneth = score % 10;

                // get the middle decimal digit of the score
                score /= 10;
                tenth = score % 10;

                // get the highest decimal digit of the score
                score /= 10;
                hundredth = score;

                if (hundredth > 0) {
                    boolean[][] modelHundreth = Number.forNumber(hundredth).getNumber();
                    drawModel(canvas, modelHundreth, gap, adjustment);
                    gap += (Number.COLUMN_COUNT + 1) * blockWidth / adjustment;
                }

                if (tenth > 0) {
                    boolean[][] modelTenth = Number.forNumber(tenth).getNumber();
                    drawModel(canvas, modelTenth, gap, adjustment);
                    gap += (Number.COLUMN_COUNT + 1) * blockWidth / adjustment;
                }

                modelOneth = Number.forNumber(oneth).getNumber();
                drawModel(canvas, modelOneth, gap, adjustment);
            }
        }

        /**
         * Draw each digit as a block adjusted by 2
         * @param canvas
         * @param model
         * @param startX
         * @param adjustment
         */
        private void drawModel(Canvas canvas, boolean[][] model, int startX, int adjustment){
            for (int i = 0; i < model.length; i++) {
                for (int j = 0; j < model[i].length; j++) {
                    if(model[i][j]) {
                        int blockY = (i * (blockHeight / adjustment)) + context.getBorder();
                        int blockX = (j * (blockWidth / adjustment)) + context.getBorder() + startX;
                        BlockDrawable b = new BlockDrawable(blockX, blockY, blockWidth, blockHeight, adjustment);
                        Rotatable f = GameContext.PICKER.peek();
                        b.setSpecificColor(GameColor.BLACK.getColor());
                        b.setSpecificBlockStroke(blockStroke/adjustment);
                        b.draw(canvas);
                    }
                }
            }
        }
    }
}
