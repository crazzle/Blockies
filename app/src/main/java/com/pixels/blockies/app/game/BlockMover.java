package com.pixels.blockies.app.game;

import android.util.Log;

import com.pixels.blockies.app.draws.Block;

/**
 * Created by keinmark on 08.03.14.
 */
public class BlockMover implements Runnable {
    Block block = null;
    private int groundLimit = -1;

    @Override
    public void run() {
        while (true) {
            doHandle();
            delay();
        }
    }

    private void doHandle() {
        if (isBlockSet() && isGroundSet()) {
            moveBlockDown();
        }
    }

    private void moveBlockDown() {
        int currentY = block.getY();
        int newY = block.getHeight() + currentY;
        if(!isGroundReached(newY)) {
            block.setY(newY);
        }else{
            removeBlock();
        }
    }

    private boolean isGroundReached(int newY) {
        return !(newY < groundLimit);
    }

    private boolean isGroundSet() {
        return groundLimit != -1;
    }

    private boolean isBlockSet() {
        return block != null;
    }

    private void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setGroundLimit(int groundLimit) {
        this.groundLimit = groundLimit;
    }

    public void setBlock(Block b) {
        this.block = b;
    }

    public void removeBlock() {
        this.block = null;
    }
}
