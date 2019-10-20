package com.bouygtel.sunlite.scene.entities;

import com.bouygtel.sunlite.scene.values.Color;
import com.bouygtel.sunlite.usb.SunliteController;

public class Projecteur {

    private int firstChannel;

    private Color color;

    public Projecteur(int firstChannel) {
        this.firstChannel = firstChannel;
        color = new Color(0, 0, 0,0);
    }

    public int getFirstChannel() {
        return firstChannel;
    }

    public void setFirstChannel(int firstChannel) {
        this.firstChannel = firstChannel;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void updateValues(SunliteController controller) {
        controller.setValue(firstChannel, (char) color.getRed());
        controller.setValue(firstChannel+1, (char) color.getGreen());
        controller.setValue(firstChannel+2, (char) color.getBlue());
        controller.setValue(firstChannel+3, (char) color.getWhite());
    }
}
