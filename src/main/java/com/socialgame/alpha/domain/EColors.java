package com.socialgame.alpha.domain;

public enum EColors {

    COLOR_RED,
    COLOR_BLUE,
    COLOR_GREEN,
    COLOR_YELLOW,
    COLOR_TEAL,
    COLOR_PINK,
    COLOR_ORANGE,
    COLOR_PURPLE;

    public static String[] colors() {
        EColors[] values = values();
        String[] colors = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            colors[i] = values[i].name();
        }

        return colors;
    }

    public void newPlayerColor(int lobbySize) {

    }

    public void toggleColor(int lobbySize) {

    }


}
