package com.pixels.blockies.app.draws;

import android.graphics.Canvas;
import com.pixels.blockies.app.draws.api.Drawable;
import com.pixels.blockies.app.draws.enums.GameColor;
import com.pixels.blockies.app.draws.enums.Letter;

public class RestartScreenDrawable implements Drawable {

    public static final float MINI_BLOCK_FACTOR = 1.5f;

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
            next+=((TAP.length()+1)*blockWidth);
        }
    }

    public void drawChar(Canvas canvas, char letter, int xPos, int yPos){
        boolean[][] model = Letter.forLetter(letter).getLetter();
        for (int i = 0; i < model.length; i++) {
            for (int j = 0; j < model[i].length; j++) {
                if (model[i][j]) {
                    int blockY = (i * blockHeight) + yPos;
                    int blockX = (j * blockWidth) + xPos;
                    BlockDrawable b = new BlockDrawable(blockX, blockY, this.blockWidth, this.blockHeight);
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
        this.xCenter = (maxWidth/2)-Letter.COLUMN_COUNT*2*this.blockWidth;
        this.yCenter = (maxHeight/2)-Letter.COLUMN_COUNT*this.blockHeight;
    }

    public void setStrokeThickness(int thickness){
        this.strokeThickness = thickness;
    }

}
