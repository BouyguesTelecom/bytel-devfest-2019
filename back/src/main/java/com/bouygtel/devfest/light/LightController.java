package com.bouygtel.devfest.light;

import com.bouygtel.devfest.light.beans.Color;
import com.bouygtel.devfest.light.client.ChangeColorClient;
import com.bouygtel.devfest.light.client.in.ColorIn;
import org.springframework.scheduling.annotation.Scheduled;

public class LightController {

    private final ChangeColorClient changeColorClient;

    private final ColorGradient colorGradient;

    private final boolean active;

    public LightController(ChangeColorClient changeColorClient, ColorGradient colorGradient, boolean active) {
        this.changeColorClient = changeColorClient;
        this.colorGradient = colorGradient;
        this.active = active;
    }

    private Color currentColor;

    private Color targetColor;


    public void changeValue(double value) {
        targetColor = colorGradient.calculate(value);
    }

    @Scheduled(fixedDelay = 20)
    private void update() {
        if (!active) {
            return;
        }
        if (targetColor == null) {
            return;
        }
        if (targetColor.equals(currentColor)) {
            return;
        }
        updateColor(targetColor);
    }

    private void updateColor(Color color) {
        changeColorClient.changeColor(mapColor(color));
        currentColor = color;
    }

    private ColorIn mapColor(Color color) {
        ColorIn colorIn = new ColorIn();
        colorIn.setRed(color.getRed());
        colorIn.setGreen(color.getGreen());
        colorIn.setBlue(color.getBlue());
        colorIn.setWhite(color.getWhite());
        return colorIn;
    }
}
