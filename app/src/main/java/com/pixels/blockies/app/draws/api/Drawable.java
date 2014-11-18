package com.pixels.blockies.app.draws.api;

import android.graphics.Canvas;

/**
 * The interface is implemented by all components that should be drawn
 * on the screen. It implies that every component knows how to draw
 * itself.
 */
public interface Drawable {
    public void draw(Canvas canvas);
}
