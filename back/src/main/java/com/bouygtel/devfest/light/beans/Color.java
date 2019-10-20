package com.bouygtel.devfest.light.beans;

import java.util.Objects;

public class Color {

    private final int red;

    private final int green;

    private final int blue;

    private final int white;

    public Color(int red, int green, int blue, int white) {
        validateValue(red);
        validateValue(green);
        validateValue(blue);
        validateValue(white);

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.white = white;
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getWhite() {
        return white;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Color color = (Color) o;
        return red == color.red &&
                green == color.green &&
                blue == color.blue &&
                white == color.white;
    }

    @Override
    public int hashCode() {
        return Objects.hash(red, green, blue, white);
    }

    @Override
    public String toString() {
        return "Color{" +
                "red=" + red +
                ", green=" + green +
                ", blue=" + blue +
                ", white=" + white +
                '}';
    }

    private void validateValue(int value) {
        if (value < 0 || value > 255) {
            throw new IllegalArgumentException("Une couleur doit avoir une valeur entre 0 et 255");
        }
    }
}
