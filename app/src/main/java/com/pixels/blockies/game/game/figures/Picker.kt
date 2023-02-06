package com.pixels.blockies.game.game.figures

import java.util.*

class Picker {
    private val random = Random()
    private val queue: Queue<Rotatable?> = LinkedList()

    init {
        queue.add(get())
    }

    private fun get(): Rotatable? {
        val num = random.nextInt(9)
        var picked: Rotatable? = null
        when (num) {
            0 -> picked = FigureI()
            1 -> picked = FigureJ()
            2 -> picked = FigureL()
            3 -> picked = FigureT()
            4 -> picked = FigureZ()
            5 -> picked = FigureZRevert()
            6 -> picked = FigureO()
            7 -> picked = FigureSplitLine()
            8 -> picked = FigureSingleDot()
        }
        return picked
    }

    @Synchronized
    fun pick(): Rotatable? {
        queue.add(get())
        return queue.poll()
    }

    @Synchronized
    fun peek(): Rotatable? {
        return queue.peek()
    }
}