package com.pixels.blockies.app.draws.enums;

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
    }, 'S');

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
