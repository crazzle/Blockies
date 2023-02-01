package com.pixels.blockies.game.game

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

/**
 * The core component of the game, responsible for moving the blocks around
 */
class BlockMover : Runnable {
    /**
     * Scheduler to shift blocks based on a specific time interval
     */
    private val scheduler = Executors.newScheduledThreadPool(1)
    private var schedule: ScheduledFuture<*>? = null

    /**
     * The underlying logical grid
     */
    private val grid: Grid? = Grid.Companion.getInstance()

    /**
     * The current block that is moved by the mover
     */
    private var block: Block? = null

    /**
     * Picker that picks the figure for the next block
     */
    private val picker = GameContext.PICKER

    /**
     * The sage who is able to say if a
     * if a line is full
     */
    private val sage = Sage()

    /**
     * Indicates if a game is finished or not
     */
    private var lost = false

    /**
     * the current interval of the schedule
     */
    private var interval = 750

    /**
     * difference that the interval will be reduced
     * on next level
     */
    private val INTERVAL_DIFF = 20 // 25 Levels

    init {
        putNewBlockInGame()
    }

    /**
     * Starts the block mover so that it moves a block
     * for every X seconds
     */
    fun start() {
        createNewSchedule(this, interval)
    }

    @Synchronized
    override fun run() {
        if (!lost) {
            if (isBlockInGame()) {
                moveBlockDown()
            } else {
                val valid = putNewBlockInGame()
                lost = !valid
                if (lost) {
                    GameContext.HIGH_SCORE!!.saveScore(GameContext.getScore())
                }
            }
        } else {
            pauseMoving()
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
    @Synchronized
    fun moveBlockDown() {
        if (isBlockInGame()) {
            removeBlockFromGrid()
            val notFinished = !isGroundReached() && !isNextOccupied()
            if (notFinished) {
                var currentRow = block!!.getY()
                block!!.setY(++currentRow)
            }
            addBlockToGrid()
            if (!notFinished) {
                removeBlock()
                val completed = sage.checkForCompleteLines()
                if (completed!!.size > 0) {
                    grid!!.shiftRemoveCompleted(completed)
                    GameContext.addToScore(completed.size)
                    for (i in completed.indices) {
                        if (interval - INTERVAL_DIFF > MIN_INTERVAL) {
                            interval -= INTERVAL_DIFF
                            createNewSchedule(this, interval)
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if the next position (vertically) of the current block is occupied
     */
    private fun isNextOccupied(): Boolean {
        var check = false
        for (i in 0 until block!!.getOffsetX()) {
            for (j in 0 until block!!.getOffsetY()) {
                if (block!!.getInner(i, j) > Grid.Companion.EMPTY) {
                    if (grid!!.getPositionValue(
                            i + block!!.getX(),
                            j + 1 + block!!.getY()
                        ) > Grid.Companion.EMPTY
                    ) {
                        check = true
                    }
                }
            }
        }
        return check
    }

    /**
     * Adds the current block to the grid
     */
    private fun addBlockToGrid() {
        for (i in 0 until block!!.getOffsetX()) {
            for (j in 0 until block!!.getOffsetY()) {
                if (block!!.getY() + j < GameContext.VERTICAL_BLOCK_COUNT) {
                    if (block!!.getInner(i, j) != Grid.Companion.EMPTY) {
                        grid!!.add(block!!.getX() + i, block!!.getY() + j, block!!.getInner(i, j))
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
    @Synchronized
    fun moveHorizontalPosition(offset: Int) {
        if (isBlockInGame()) {
            removeBlockFromGrid()
            if (!isHorizontalNeighborOccupied(offset)) {
                block!!.setX(block!!.getX() + offset)
            }
            addBlockToGrid()
        }
    }

    /**
     * Checks if the next position (horizontally) of the current block is occupied.
     * The method checks also the boundaries of the grid.
     */
    private fun isHorizontalNeighborOccupied(offset: Int): Boolean {
        var check = false
        if (block!!.getX() + offset < 0) {
            check = true
        } else if (block!!.getX() + offset + block!!.getOffsetX() > GameContext.HORIZONTAL_BLOCK_COUNT) {
            check = true
        } else {
            for (i in 0 until block!!.getOffsetX()) {
                for (j in 0 until block!!.getOffsetY()) {
                    if (block!!.getInner(i, j) > Grid.Companion.EMPTY) {
                        if (grid!!.getPositionValue(
                                block!!.getX() + i + offset,
                                j + block!!.getY()
                            ) > Grid.Companion.EMPTY
                        ) {
                            check = true
                        }
                    }
                }
            }
        }
        return check
    }

    /**
     * Removes the old position from the grid
     */
    private fun removeBlockFromGrid() {
        for (i in 0 until block!!.getOffsetX()) {
            for (j in 0 until block!!.getOffsetY()) {
                if (block!!.getInner(i, j) != Grid.Companion.EMPTY) {
                    grid!!.remove(block!!.getX() + i, block!!.getY() + j)
                }
            }
        }
    }

    /**
     * Puts a new block into the game by initializing
     * the block variable
     */
    @Synchronized
    fun putNewBlockInGame(): Boolean {
        val b = Block(picker!!.pick())
        b.setY(0)
        b.setX(GameContext.HORIZONTAL_BLOCK_COUNT / 2)
        var check = true
        for (i in 0 until b.getOffsetX()) {
            for (j in 0 until b.getOffsetY()) {
                if (b.getInner(i, j) > Grid.Companion.EMPTY) {
                    if (grid!!.getPositionValue(
                            i + b.getX(),
                            j + b.getY()
                        ) > Grid.Companion.EMPTY
                    ) {
                        check = false
                    }
                }
            }
        }
        if (check) {
            block = b
        }
        return check
    }

    /**
     * Checks if a block is currently in game
     */
    private fun isBlockInGame(): Boolean {
        return block != null
    }

    /**
     * Checks if the ground is reached
     */
    private fun isGroundReached(): Boolean {
        var check = false
        if (block!!.getY() + block!!.getOffsetY() >= GameContext.VERTICAL_BLOCK_COUNT) {
            check = true
        }
        return check
    }

    /**
     * Removes the current block from the game
     */
    private fun removeBlock() {
        block = null
    }

    /**
     * Rotates the current block by:
     * s removing the block from the grid
     * - rotating the block
     * - adding the block to the grid
     */
    @Synchronized
    fun rotate() {
        if (isBlockInGame()) {
            removeBlockFromGrid()
            if (isRotatable()) {
                block!!.rotate()
            }
            addBlockToGrid()
        }
    }

    /**
     * Checks if a block is rotatable in its current position
     */
    private fun isRotatable(): Boolean {
        var check = true
        if (block!!.getX() + block!!.rotatedOffsetX < 0) {
            check = false
        } else if (block!!.getX() + block!!.rotatedOffsetX > GameContext.HORIZONTAL_BLOCK_COUNT) {
            check = false
        } else if (block!!.getY() + block!!.rotatedOffsetY > GameContext.VERTICAL_BLOCK_COUNT) {
            check = false
        } else {
            for (i in 0 until block!!.rotatedOffsetX) {
                for (j in 0 until block!!.rotatedOffsetY) {
                    if (block!!.getRotatedInner(i, j) > Grid.Companion.EMPTY) {
                        if (grid!!.getPositionValue(
                                i + block!!.getX(),
                                j + block!!.getY()
                            ) > Grid.Companion.EMPTY
                        ) {
                            check = false
                        }
                    }
                }
            }
        }
        return check
    }

    /**
     * Checks if the game has ended
     */
    @Synchronized
    fun hasEnded(): Boolean {
        return lost
    }

    /**
     * Restarts the game logic
     */
    @Synchronized
    fun restart() {
        grid!!.initLogicalGrid()
        GameContext.reset()
        lost = false
        interval = MAX_INTERVAL
        createNewSchedule(this, interval)
    }

    /**
     * Pauses the game by canceling the current schedule
     */
    fun pauseMoving() {
        schedule!!.cancel(true)
    }

    /**
     * resumes the game by creating a new schedule
     */
    fun resumeMoving() {
        createNewSchedule(this, interval)
    }

    /**
     * Creates a new schedule
     */
    private fun createNewSchedule(handling: Runnable, interval: Int) {
        if (schedule != null) {
            schedule!!.cancel(true)
        }
        schedule =
            scheduler.scheduleAtFixedRate(handling, 0, interval.toLong(), TimeUnit.MILLISECONDS)
    }

    companion object {
        /**
         * the minimum playable interval
         */
        private const val MIN_INTERVAL = 150

        /**
         * the maximum playable interval
         */
        private const val MAX_INTERVAL = 750
    }
}