package com.bouygtel.sunlite.scene.values;

public class Color {

    private int red;

    private int green;

    private int blue;

    private int white;

    public Color(int red, int green, int blue, int white) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.white = white;
    }


    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        this.blue = blue;
    }

    public int getWhite() {
        return white;
    }

    public void setWhite(int white) {
        this.white = white;
    }
}
