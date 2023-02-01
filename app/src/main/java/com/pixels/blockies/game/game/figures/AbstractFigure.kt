package com.pixels.blockies.game.game.figures

abstract class AbstractFigure : Rotatable {
    protected var currentRotation = 0
        private set
    protected abstract val figureCount: Int
    protected val nextRotation: Int
        protected get() = if (currentRotation < figureCount - 1) {
            currentRotation + 1
        } else {
            0
        }

    override fun rotate() {
        if (currentRotation < figureCount - 1) {
            currentRotation++
        } else {
            currentRotation = 0
        }
    }
}