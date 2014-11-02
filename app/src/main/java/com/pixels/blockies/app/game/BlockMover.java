package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by keinmark on 08.03.14.
 */
public class BlockMover implements Runnable {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    Grid grid = Grid.getInstance();
    Block block = null;

    public void start() {
        final Runnable handling = this;
        final ScheduledFuture moverHandling = scheduler.scheduleAtFixedRate(handling, 0, 500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void run() {
        if (isBlockInGame()) {
            moveBlockDown();
        }else{
            putNewBlockInGame();
        }
    }

    public synchronized void moveBlockDown() {
        if (block != null && !isGroundReachedOnNext() && !isNextOccupied()) {
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
        if(isBlockInGame() && !isHorizontalNeighborOccupied(block.getX()+x)){
            removeOldPosition();
            block.setX(block.getX()+x);
            addNewPosition();
        }
    }

    private boolean isHorizontalNeighborOccupied(int x) {
        return x < 0 || x > StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT-1 || grid.getPositionValue(x, block.getY()) == 1;
    }

    private void removeOldPosition() {
        int x = block.getX();
        int y = block.getY();
        grid.remove(x, y);
    }

    public void putNewBlockInGame() {
        Block b = new Block();
        b.setY(0);
        b.setX(StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT/2);
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
