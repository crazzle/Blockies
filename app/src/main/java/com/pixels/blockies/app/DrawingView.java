package com.pixels.blockies.app;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import com.pixels.blockies.app.draws.Block;
import com.pixels.blockies.app.draws.Drawable;
import com.pixels.blockies.app.draws.Line;
import com.pixels.blockies.app.game.BlockMover;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by keinmark on 08.03.14.
 */
public class DrawingView extends View {
    int width = -1;
    int height = -1;
    int ground = -1;
    int viewBorder = 5;

    private BlockMover blockMover = null;
    boolean isInit = false;

    private List<Drawable> drawObjects = new ArrayList<Drawable>();

    Block b = null;
    Line groundLine = null;

    public DrawingView(Context context) {
        super(context);
    }

    public void init() {
        if (!isInit) {
            int blockHeight = (height - 2 * viewBorder) / 20;
            int blockWidth = (width - 2 * viewBorder) / 10;
            b = new Block(blockWidth, blockHeight, 5, 5);
            ground = 20 * blockHeight+5;
            groundLine = new Line(width, ground, 0, ground);
            drawObjects.add(b);
            drawObjects.add(groundLine);
            if (blockMover != null) {
                blockMover.setBlock(b);
                blockMover.setGroundLimit(ground);
            }
            isInit = true;
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        for (Drawable d : drawObjects) {
            d.draw(canvas);
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        width = xNew;
        height = yNew;
    }

    public void setBlockMover(BlockMover blockMover) {
        this.blockMover = blockMover;
    }
}
