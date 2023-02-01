package com.pixels.blockies.game.game

/**
 * The logical grid behaves as the gameboard that contains the blocks
 */
class Grid private constructor() {
    /**
     * The actual grid
     */
    private val logicalGrid =
        Array(GameContext.HORIZONTAL_BLOCK_COUNT) { IntArray(GameContext.VERTICAL_BLOCK_COUNT) }

    /**
     * Private constructor limiting the instances
     */
    init {
        initLogicalGrid()
    }

    /**
     * Initializes the logical grid with -1
     * (-1 means the field is empty)
     */
    @Synchronized
    fun initLogicalGrid() {
        for (i in logicalGrid.indices) {
            for (j in logicalGrid[i].indices) {
                logicalGrid[i][j] = EMPTY
            }
        }
    }

    @Synchronized
    fun add(x: Int, y: Int, value: Int) {
        logicalGrid[x][y] = value
    }

    @Synchronized
    fun remove(x: Int, y: Int) {
        logicalGrid[x][y] = -1
    }

    @Synchronized
    fun getPositionValue(x: Int, y: Int): Int {
        return logicalGrid[x][y]
    }

    /**
     * Removes the completed lines and shifts all lines above down
     */
    @Synchronized
    fun shiftRemoveCompleted(completed: List<Int>?) {
        for (index in completed!!) {
            for (column in logicalGrid) {
                val columnAsList = ArrayList<Int>()
                for (i in column.indices) {
                    columnAsList.add(column[i])
                }
                columnAsList.removeAt(index)
                columnAsList.add(0, -1)
                val strippedColumn = IntArray(GameContext.VERTICAL_BLOCK_COUNT)
                for (i in strippedColumn.indices.reversed()) {
                    strippedColumn[i] = columnAsList[i]
                }
                System.arraycopy(strippedColumn, 0, column, 0, GameContext.VERTICAL_BLOCK_COUNT)
            }
        }
    }

    companion object {
        /**
         * private instance providing singleton access
         */
        private var grid: Grid? = null

        /**
         * indicator for an empty field
         */
        const val EMPTY = -1

        /**
         * Factory method that returns the instance
         */
        fun getInstance(): Grid? {
            if (grid == null) {
                grid = Grid()
            }
            return grid
        }
    }
}