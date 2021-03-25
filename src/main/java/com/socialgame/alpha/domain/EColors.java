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
//        EColors[] values = values();
        String[] colors = new String[values().length];
        for (int i = 0; i < values().length; i++) { colors[i] = values()[i].name(); }
        return colors;
    }



}
