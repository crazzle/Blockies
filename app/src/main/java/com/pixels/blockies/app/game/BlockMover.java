package com.pixels.blockies.app.game;

import com.pixels.blockies.app.environment.StaticGameEnvironment;
import com.pixels.blockies.app.game.figures.Picker;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by keinmark on 08.03.14.
 */
public class BlockMover implements Runnable {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    Grid grid = Grid.getInstance();
    Block block = null;
    Picker picker = new Picker();

    public void start() {
        final Runnable handling = this;
        scheduler.scheduleAtFixedRate(handling, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Override
    public synchronized void run() {
        if (isBlockInGame()) {
            moveBlockDown();
        } else {
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
        boolean check = false;
        for (int i = 0; i < block.getOffsetX(); i++) {
            for (int j = 0; j < block.getOffsetY(); j++) {
                int nextRow = j + 1;
                boolean isLastRow = nextRow >= block.getOffsetY();
                boolean compoundBlocksNotItself = nextRow < block.getOffsetY() && !(block.getInner(i, nextRow) == 1);
                if (block.getInner(i, j) == 1 && (isLastRow || compoundBlocksNotItself)) {
                    if (grid.getPositionValue(i + block.getX(), j + 1 + block.getY()) == 1) {
                        check = true;
                    }
                }
            }
        }
        return check;
    }

    private void addNewPosition() {
        for (int i = 0; i < block.getOffsetX(); i++) {
            for (int j = 0; j < block.getOffsetY(); j++) {
                if (block.getY() + j < StaticGameEnvironment.VERTICAL_BLOCK_COUNT) {
                    if (block.getInner(i, j) != 0) {
                        grid.add(block.getX() + i, block.getY() + j, block.getInner(i, j));
                    }
                }
            }
        }
    }

    public synchronized void moveHorizontalPosition(int offset) {
        if (isBlockInGame() && !isHorizontalNeighborOccupied(offset)) {
            removeOldPosition();
            block.setX(block.getX() + offset);
            addNewPosition();
        }
    }

    private boolean isHorizontalNeighborOccupied(int offset) {
        boolean check = false;
        if (block.getX() + offset < 0) {
            check = true;
        } else if (block.getX() + offset + block.getOffsetX() > StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT) {
            check = true;
        } else {
            for (int i = 0; i < block.getOffsetX(); i++) {
                for (int j = 0; j < block.getOffsetY(); j++) {
                    int nextColumn = i + offset;
                    boolean isLastColumn = offset < 0 ? nextColumn < 0 : nextColumn >= block.getOffsetX();
                    boolean compoundBlocksNotItself = (offset < 0 ? nextColumn >= 0 : nextColumn < block.getOffsetX())
                            && !(block.getInner(nextColumn, j) == 1);
                    if (block.getInner(i, j) == 1 && (isLastColumn || compoundBlocksNotItself)) {
                        if (grid.getPositionValue(block.getX() + i + offset, j + block.getY()) == 1) {
                            check = true;
                        }
                    }
                }
            }
        }
        return check;
    }

    private void removeOldPosition() {
        for (int i = 0; i < block.getOffsetX(); i++) {
            for (int j = 0; j < block.getOffsetY(); j++) {
                if (block.getInner(i, j) != 0) {
                    grid.remove(block.getX() + i, block.getY() + j);
                }
            }
        }
    }

    public synchronized void putNewBlockInGame() {
        Block b = new Block(picker.pick());
        b.setY(0);
        b.setX(StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT / 2);
        this.block = b;
    }

    private boolean isBlockInGame() {
        return block != null;
    }

    private boolean isGroundReachedOnNext() {
        boolean check = false;
        if ((block.getY() + block.getOffsetY()) >= StaticGameEnvironment.VERTICAL_BLOCK_COUNT) {
            check = true;
        }
        return check;
    }

    private void removeBlock() {
        this.block = null;
    }

    public synchronized int getCurrentX() {
        if (isBlockInGame()) {
            return this.block.getX() + block.getOffsetX() / 2;
        } else {
            return 0;
        }
    }

    public synchronized int getCurrentY() {
        if (isBlockInGame()) {
            return this.block.getY() + block.getOffsetY() / 2;
        } else {
            return 0;
        }
    }

    public synchronized void rotate() {
        if (isBlockInGame() && isRotatable()) {
            removeOldPosition();
            this.block.rotate();
            addNewPosition();
        }
    }

    private boolean isRotatable() {
        boolean check = true;
        if (block.getX() + block.getRotatedOffsetX() < 0) {
            check = false;
        } else if (block.getX() + block.getRotatedOffsetX() > StaticGameEnvironment.HORIZONTAL_BLOCK_COUNT) {
            check = false;
        } else if (block.getY() + block.getRotatedOffsetY() > StaticGameEnvironment.VERTICAL_BLOCK_COUNT) {
            check = false;
        } else {
            for (int i = 0; i < block.getRotatedOffsetX(); i++) {
                for (int j = 0; j < block.getRotatedOffsetY(); j++) {
                    if (block.getRotatedInner(i, j) == 1) {
                        if (grid.getPositionValue(i + block.getRotatedOffsetX(), j + block.getRotatedOffsetY()) == 1) {
                            check = false;
                        }
                    }
                }
            }
        }
        return check;
    }

}
