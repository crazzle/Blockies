package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;

/**
 * Created by keinmark on 08.03.14.
 */
public class BlockMover implements Runnable {
    Grid grid = Grid.getInstance();
    Block block = null;

    @Override
    public void run() {
        while (true) {
            doHandle();
            delay();
        }
    }

    private void doHandle() {
        if (isBlockInGame()) {
            moveBlockDown();
        }
    }

    private void moveBlockDown() {
        if (!isGroundReachedOnNext()) {
            removeOldPosition();
            int currentRow = block.getY();
            block.setY(++currentRow);
            addNewPosition();
        } else {
            removeBlock();
        }
    }

    private void addNewPosition() {
        int x = block.getX();
        int y = block.getY();
        grid.add(x, y);
    }

    private void removeOldPosition() {
        int x = block.getX();
        int y = block.getY();
        grid.remove(x, y);
    }

    private void delay() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void putNewBlockInGame(Block block) {
        this.block = block;
    }

    public boolean isBlockInGame() {
        return block != null;
    }

    private boolean isGroundReachedOnNext() {
        return (block.getY()+1) >= StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT;
    }

    private void removeBlock() {
        this.block = null;
    }

}
