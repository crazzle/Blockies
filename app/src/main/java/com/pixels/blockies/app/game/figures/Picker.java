package com.pixels.blockies.app.game.figures;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Picker {
    private Random random = new Random();
    private Queue<Rotatable> queue = new LinkedList<Rotatable>();

    public Picker() {
        queue.add(get());
    }

    private Rotatable get() {
        int num = random.nextInt(6);
        Rotatable picked = null;
        switch (num) {
            case 0:
                picked = new FigureI();
                break;
            case 1:
                picked = new FigureJ();
                break;
            case 2:
                picked = new FigureL();
                break;
            case 3:
                picked = new FigureT();
                break;
            case 4:
                picked = new FigureZ();
                break;
            case 5:
                picked = new FigureO();
                break;
        }
        return picked;
    }

    public synchronized Rotatable pick() {
        queue.add(get());
        return queue.poll();
    }

    public synchronized Rotatable peek() {
        return queue.peek();
    }

}
