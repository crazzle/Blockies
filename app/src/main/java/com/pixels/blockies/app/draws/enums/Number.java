package com.pixels.blockies.app.draws.enums;

/**
 * Enum with all numbers that are
 * drawn using blocks
 */
public enum Number {
    ZERO(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, false, true},
            {true, false, true},
            {true, true, true}
    }),
    ONE(new boolean[][]{
            {false, false, true},
            {false, false, true},
            {false, false, true},
            {false, false, true},
            {false, false, true}
    }),
    TWO(new boolean[][]{
            {true, true, true},
            {false, false, true},
            {true, true, true},
            {true, false, false},
            {true, true, true}
    }),
    THREE(new boolean[][]{
            {true, true, true},
            {false, false, true},
            {true, true, true},
            {false, false, true},
            {true, true, true}
    }),
    FOUR(new boolean[][]{
            {true, false, true},
            {true, false, true},
            {true, true, true},
            {false, false, true},
            {false, false, true}
    }),
    FIVE(new boolean[][]{
            {true, true, true},
            {true, false, false},
            {true, true, true},
            {false, false, true},
            {true, true, true}
    }),
    SIX(new boolean[][]{
            {true, true, true},
            {true, false, false},
            {true, true, true},
            {true, false, true},
            {true, true, true}
    }),
    SEVEN(new boolean[][]{
            {true, true, true},
            {false, false, true},
            {false, false, true},
            {false, false, true},
            {false, false, true}
    }),
    EIGHT(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, true, true},
            {true, false, true},
            {true, true, true}
    }),
    NINE(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, true, true},
            {false, false, true},
            {true, true, true}
    });

    public static final int COLUMN_COUNT = 3;

    private boolean[][] field;

    private Number(boolean[][] field) {
        this.field = field;
    }

    public static Number forNumber(int number) {
        return values()[number];
    }

    public boolean[][] getNumber() {
        return field;
    }

}
