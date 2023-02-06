package com.pixels.blockies.game.game.figures

interface Rotatable {
    fun rotate()
    fun get(): Array<IntArray?>
    fun getNext(): Array<IntArray?>
}