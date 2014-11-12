package com.pixels.blockies.app.draws;

/**
 * Created by mark on 12.11.14.
 */
public enum Button {
    PAUSE(new boolean[][]{
            {true, false, true},
            {true, false, true},
            {true, false, true},
            {true, false, true},
            {true, false, true}
    });


    private boolean[][] field;

    private Button(boolean[][] field) {
        this.field = field;
    }

    public boolean[][] getButton() {
        return field;
    }

}
