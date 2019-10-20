package com.bouygtel.devfest.light;

import com.bouygtel.devfest.light.beans.Color;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class ColorGradient {

    /**
     * Parse un gradient au format value,r,g,b,w;value,r,g,b,w...
     * @param value
     * @return
     */
    public static ColorGradient parse(String value) {
        List<Step> steps = new LinkedList<>();
        for (String step : value.trim().split(";")) {
            String[] stepValues = step.split(",");
            if (stepValues.length != 5) {
                continue;
            }
            steps.add(new Step(
                    Integer.parseInt(stepValues[0]),
                    new Color(
                            Integer.parseInt(stepValues[1]),
                            Integer.parseInt(stepValues[2]),
                            Integer.parseInt(stepValues[3]),
                            Integer.parseInt(stepValues[4])
                    )
            ));
        }
        return new ColorGradient(steps);
    }

    private static class Step {
        private final int value;
        private final Color color;
        public Step(int value, Color color) {
            this.value = value;
            this.color = color;
        }

        public int getValue() {
            return value;
        }

        public Color getColor() {
            return color;
        }
    }

    private final List<Step> steps;

    private ColorGradient(List<Step> steps) {
        this.steps = steps;
    }

    public Color calculate(double value) {
        Step lowerStep = findLowerStep(value);
        Step greaterStep = findGreaterStep(value);
        if (lowerStep == null && greaterStep == null) {
            return new Color(0,0,0,0);
        }
        if (lowerStep != null && greaterStep == null) {
            return lowerStep.getColor();
        }
        if (greaterStep != null && lowerStep == null) {
            return greaterStep.getColor();
        }

        double ratio = (value-lowerStep.getValue())/((double) (greaterStep.getValue()-lowerStep.getValue()));
        return calculateColorRatio(lowerStep.getColor(), greaterStep.getColor(), ratio);
    }

    private Color calculateColorRatio(Color start, Color end, double ratio) {
        return new Color(
                calculateValue(start.getRed(), end.getRed(), ratio),
                calculateValue(start.getGreen(), end.getGreen(), ratio),
                calculateValue(start.getBlue(), end.getBlue(), ratio),
                calculateValue(start.getWhite(), end.getWhite(), ratio)
        );
    }

    private int calculateValue(int start, int end, double ratio) {
        return (int) (start + (end-start)*ratio);
    }


    private Step findLowerStep(double value) {
        return steps.stream()
                .filter(step->step.getValue()<=value)
                .max(Comparator.comparing(Step::getValue))
                .orElse(null);
    }

    private Step findGreaterStep(double value) {
        return steps.stream()
                .filter(step->step.getValue()>value)
                .min(Comparator.comparing(Step::getValue))
                .orElse(null);
    }

}
