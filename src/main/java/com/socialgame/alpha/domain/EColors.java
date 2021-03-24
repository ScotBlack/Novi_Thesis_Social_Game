package com.socialgame.alpha.domain;

public enum EColors {

    RED,
    BLUE,
    GREEN,
    YELLOW,
    TEAL,
    PINK,
    ORANGE,
    PURPLE;

    public static String[] colors() {
        EColors[] values = values();
        String[] colors = new String[values.length];

        for (int i = 0; i < values.length; i++) {
            colors[i] = values[i].name();
        }

        return colors;
    }

    public static String toggleColor(String color) {
        String[] colors = colors();

        for (int i = 0; i < colors.length; i++) {
            if (color.equals(colors[i])) {
                if (i == 7) {
                    return colors[0];
                } else {
                    return colors[i + 1];
                }
            }
        }
        throw new IllegalArgumentException();
    }


    public void newPlayerColor(int lobbySize) {

    }
}
