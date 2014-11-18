package com.pixels.blockies.app.game;

import com.pixels.blockies.app.game.figures.Picker;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * The core component of the game, responsible for moving the blocks around
 */
public class BlockMover implements Runnable {

    /**
     * Scheduler to shift blocks based on a specific time interval
     */
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private ScheduledFuture schedule = null;

    /**
     * The underlying logical grid
     */
    private Grid grid = Grid.getInstance();

    /**
     * The current block that is moved by the mover
     */
    private Block block = null;

    /**
     * Picker that picks the figure for the next block
     */
    private Picker picker = GameContext.PICKER;

    /**
     * The sage who is able to say if a
     * if a line is full
     */
    private Sage sage = new Sage();

    /**
     * Indicates if a game is finished or not
     */
    private boolean lost = false;

    /**
     * the current interval of the schedule
     */
    private int interval = 750;

    /**
     * difference that the interval will be reduced
     * on next level
     */
    private final int INTERVAL_DIFF = 20; // 25 Levels

    /**
     * the minimum playable interval
     */
    private final static int MIN_INTERVAL = 150;

    /**
     * the maximum playable interval
     */
    private final static int MAX_INTERVAL = 750;

    public BlockMover(){
        putNewBlockInGame();
    }

    /**
     * Starts the block mover so that it moves a block
     * for every X seconds
     */
    public void start() {
        createNewSchedule(this, interval);
    }

    @Override
    public synchronized void run() {
        if(!lost) {
            if (isBlockInGame()) {
                moveBlockDown();
            } else {
                boolean valid = putNewBlockInGame();
                lost = !valid;
            }
        }else{
            pauseMoving();
        }
    }

    /**
     * Moves a block down. As long as a block is in
     * game it
     * - first removes the old position
     * - checks if the next position is valid
     * - sets the block coordinates to the new position
     * - adds the block again with the new position
     *
     * That seems like a lot of overhead, but first delete
     * is necessary so the block is not blocked by its
     * own inner blocks. {Nice sentence...}
     *
     * If the block is finished, it will be removed from
     * the blockmover and the completed lines are counted
     */
    public synchronized void moveBlockDown() {
        if (isBlockInGame()) {
            removeBlockFromGrid();
            boolean notFinished = !isGroundReached() && !isNextOccupied();
            if(notFinished) {
                int currentRow = block.getY();
                block.setY(++currentRow);
            }
            addBlockToGrid();
            if(!notFinished){
                removeBlock();
                List<Integer> completed = sage.checkForCompleteLines();
                if(completed.size() > 0) {
                    grid.shiftRemoveCompleted(completed);
                    GameContext.addToScore(completed.size());
                    for(int i = 0; i < completed.size(); i++){
                        if((interval-INTERVAL_DIFF) > MIN_INTERVAL) {
                            interval -= INTERVAL_DIFF;
                            createNewSchedule(this, interval);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the next position (vertically) of the current block is occupied
     */
    private boolean isNextOccupied() {
        boolean check = false;
        for (int i = 0; i < block.getOffsetX(); i++) {
            for (int j = 0; j < block.getOffsetY(); j++) {
                if (block.getInner(i, j) > Grid.EMPTY) {
                    if (grid.getPositionValue(i + block.getX(), j + 1 + block.getY()) > Grid.EMPTY) {
                        check = true;
                    }
                }
            }
        }
        return check;
    }

    /**
     * Adds the current block to the grid
     */
    private void addBlockToGrid() {
        for (int i = 0; i < block.getOffsetX(); i++) {
            for (int j = 0; j < block.getOffsetY(); j++) {
                if (block.getY() + j < GameContext.VERTICAL_BLOCK_COUNT) {
                    if (block.getInner(i, j) != Grid.EMPTY) {
                        grid.add(block.getX() + i, block.getY() + j, block.getInner(i, j));
                    }
                }
            }
        }
    }

    /**
     * Moves the Block horizontally by:
     * - removing the current position
     * - adding the offset to the x coordinate
     * - adding the current block with the new coordinate to the grid
     */
    public synchronized void moveHorizontalPosition(int offset) {
        if (isBlockInGame()) {
            removeBlockFromGrid();
            if(!isHorizontalNeighborOccupied(offset)) {
                block.setX(block.getX() + offset);
            }
            addBlockToGrid();
        }
    }

    /**
     * Checks if the next position (horizontally) of the current block is occupied.
     * The method checks also the boundaries of the grid.
     */
    private boolean isHorizontalNeighborOccupied(int offset) {
        boolean check = false;
        if (block.getX() + offset < 0) {
            check = true;
        } else if (block.getX() + offset + block.getOffsetX() > GameContext.HORIZONTAL_BLOCK_COUNT) {
            check = true;
        } else {
            for (int i = 0; i < block.getOffsetX(); i++) {
                for (int j = 0; j < block.getOffsetY(); j++) {
                    if (block.getInner(i, j) > Grid.EMPTY) {
                        if (grid.getPositionValue(block.getX() + i + offset, j + block.getY()) > Grid.EMPTY) {
                            check = true;
                        }
                    }
                }
            }
        }
        return check;
    }

    /**
     * Removes the old position from the grid
     */
    private void removeBlockFromGrid() {
        for (int i = 0; i < block.getOffsetX(); i++) {
            for (int j = 0; j < block.getOffsetY(); j++) {
                if (block.getInner(i, j) != Grid.EMPTY) {
                    grid.remove(block.getX() + i, block.getY() + j);
                }
            }
        }
    }

    /**
     * Puts a new block into the game by initializing
     * the block variable
     */
    public synchronized boolean putNewBlockInGame() {
        Block b = new Block(picker.pick());
        b.setY(0);
        b.setX(GameContext.HORIZONTAL_BLOCK_COUNT / 2);
        boolean check = true;
        for (int i = 0; i < b.getOffsetX(); i++) {
            for (int j = 0; j < b.getOffsetY(); j++) {
                if (b.getInner(i, j) > Grid.EMPTY) {
                    if (grid.getPositionValue(i + b.getX(), j + b.getY()) > Grid.EMPTY) {
                        check = false;
                    }
                }
            }
        }
        if(check) {
            this.block = b;
        }
        return check;
    }

    /**
     * Checks if a block is currently in game
     */
    private boolean isBlockInGame() {
        return block != null;
    }

    /**
     * Checks if the ground is reached
     */
    private boolean isGroundReached() {
        boolean check = false;
        if ((block.getY() + block.getOffsetY()) >= GameContext.VERTICAL_BLOCK_COUNT) {
            check = true;
        }
        return check;
    }

    /**
     * Removes the current block from the game
     */
    private void removeBlock() {
        this.block = null;
    }

    /**
     * Rotates the current block by:
     * s removing the block from the grid
     * - rotating the block
     * - adding the block to the grid
     */
    public synchronized void rotate() {
        if (isBlockInGame()) {
            removeBlockFromGrid();
            if(isRotatable()) {
                this.block.rotate();
            }
            addBlockToGrid();
        }
    }

    /**
     * Checks if a block is rotatable in its current position
     */
    private boolean isRotatable() {
        boolean check = true;
        if (block.getX() + block.getRotatedOffsetX() < 0) {
            check = false;
        } else if (block.getX() + block.getRotatedOffsetX() > GameContext.HORIZONTAL_BLOCK_COUNT) {
            check = false;
        } else if (block.getY() + block.getRotatedOffsetY() > GameContext.VERTICAL_BLOCK_COUNT) {
            check = false;
        } else {
            for (int i = 0; i < block.getRotatedOffsetX(); i++) {
                for (int j = 0; j < block.getRotatedOffsetY(); j++) {
                    if (block.getRotatedInner(i, j) > Grid.EMPTY) {
                        if (grid.getPositionValue(i + block.getX(), j + block.getY()) > Grid.EMPTY) {
                            check = false;
                        }
                    }
                }
            }
        }
        return check;
    }

    /**
     * Checks if the game has ended
     */
    public synchronized boolean hasEnded(){
        return lost;
    }

    /**
     * Restarts the game logic
     */
    public synchronized void restart() {
        grid.initLogicalGrid();
        GameContext.reset();
        lost = false;
        interval = MAX_INTERVAL;
        createNewSchedule(this, interval);
    }

    /**
     * Pauses the game by canceling the current schedule
     */
    public void pauseMoving() {
        schedule.cancel(true);
    }

    /**
     * resumes the game by creating a new schedule
     */
    public void resumeMoving() {
        createNewSchedule(this, interval);
    }

    /**
     * Creates a new schedule
     */
    private void createNewSchedule(Runnable handling, int interval) {
        if(schedule != null) {
            schedule.cancel(true);
        }
        schedule = scheduler.scheduleAtFixedRate(handling, 0, interval, TimeUnit.MILLISECONDS);
    }
}
