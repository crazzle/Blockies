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
        }else{
            putNewBlockInGame();
        }
    }

    private void moveBlockDown() {
        if (!isGroundReachedOnNext() && !isNextOccupied()) {
            removeOldPosition();
            int currentRow = block.getY();
            block.setY(++currentRow);
            addNewPosition();
        } else {
            removeBlock();
        }
    }

    private boolean isNextOccupied() {
        return grid.getPositionValue(block.getX(), block.getY()+1) == 1;
    }

    private void addNewPosition() {
        int x = block.getX();
        int y = block.getY();
        grid.add(x, y);
    }

    public void moveHorizontalPosition(int x){
        if(isBlockInGame()){
            removeOldPosition();
            block.setX(x);
            addNewPosition();
        }
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

    public void putNewBlockInGame() {
        Block b = new Block();
        b.setY(0);
        b.setX(0);
        this.block = b;
    }

    public boolean isBlockInGame() {
        return block != null;
    }

    private boolean isGroundReachedOnNext() {
        return (block.getY()+1) >= StaticGameEnvironment.VERTICAL_BLOCK_COUNT;
    }

    private void removeBlock() {
        this.block = null;
    }

    public void moveToBottom() {
        if(isBlockInGame()) {
            removeOldPosition();
            while(!isGroundReachedOnNext() && !isNextOccupied()){
                int y = block.getY();
                block.setY(++y);
            }
            addNewPosition();
            removeBlock();
        }
    }
}
