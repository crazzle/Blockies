package com.pixels.blockies.app.draws.enums;

/**
 * Enum with all letters that are
 * drawn using blocks
 */
public enum Letter {
    T(new boolean[][]{
            {true, true, true},
            {false, true, false},
            {false, true, false},
            {false, true, false},
            {false, true, false}
    }, 'T'),
    A(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, true, true},
            {true, false, true},
            {true, false, true}
    }, 'A'),
    B(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, true, true},
            {true, false, true},
            {true, true, true}
    }, 'B'),
    P(new boolean[][]{
            {true, true, true},
            {true, false, true},
            {true, true, true},
            {true, false, false},
            {true, false, false}
    }, 'P'),
    L(new boolean[][]{
            {true, false, false},
            {true, false, false},
            {true, false, false},
            {true, false, false},
            {true, true, true}
    }, 'L'),
    E(new boolean[][]{
            {true, true, true},
            {true, false, false},
            {true, true, true},
            {true, false, false},
            {true, true, true}
    }, 'E'),
    S(new boolean[][]{
            {true, true, true},
            {true, false, false},
            {true, true, true},
            {false, false, true},
            {true, true, true}
    }, 'S'),
    EXCLAMATION_MARK(new boolean[][]{
            {false, true, false},
            {false, true, false},
            {false, true, false},
            {false, false, false},
            {false, true, false}
    }, '!'),
    DOUBLE_POINT(new boolean[][]{
            {false, false, false},
            {false, true, false},
            {false, false, false},
            {false, true, false},
            {false, false, false}
    }, ':'),
    DASH(new boolean[][]{
            {false, false, false},
            {false, false, false},
            {true, true, true},
            {false, false, false},
            {false, false, false}
    }, '-');;

    public static final int COLUMN_COUNT = 3;

    private boolean[][] field;

    private char letter;

    private Letter(boolean[][] field, char letter) {
        this.field = field;
        this.letter = letter;
    }

    public static Letter forLetter(char letter) {
        Letter toReturn = null;
        for(Letter l : values()){
            if(l.letter == letter){
                toReturn = l;
            }
        }
        return toReturn;
    }

    public boolean[][] getLetter() {
        return field;
    }

}
