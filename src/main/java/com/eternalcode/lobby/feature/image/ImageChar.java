package com.eternalcode.lobby.feature.image;

enum ImageChar {
    BLOCK('█'),
    DARK_SHADE('▓'),
    MEDIUM_SHADE('▒'),
    TRANSPARENT_CHAT(' ');

    private final char imageChar;

    ImageChar(char imageChar) {
        this.imageChar = imageChar;
    }

    public char getChar() {
        return this.imageChar;
    }
}
