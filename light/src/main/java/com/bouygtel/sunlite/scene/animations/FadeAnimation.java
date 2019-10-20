package com.bouygtel.sunlite.scene.animations;

import com.bouygtel.sunlite.scene.entities.Projecteur;
import com.bouygtel.sunlite.scene.values.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

public class FadeAnimation {

    private static final Logger LOGGER = LoggerFactory.getLogger(FadeAnimation.class);

    private Color start;

    private Color end;

    private List<Projecteur> projecteurs;

    private int duration;

    private int position;

    public FadeAnimation(Color start, Color end, List<Projecteur> projecteurs, int duration) {
        this.start = start;
        this.end = end;
        this.projecteurs = projecteurs;
        this.duration = calculateDuration(start, end);
        this.position = 0;
    }

    public void update() {
        if (isTerminated()) {
            return;
        }
        position++;
        double ratio = ((double) position)/duration;
        Color current = new Color(
                calculate(start.getRed(), end.getRed(), ratio),
                calculate(start.getGreen(), end.getGreen(), ratio),
                calculate(start.getBlue(), end.getBlue(), ratio),
                calculate(start.getWhite(), end.getWhite(), ratio)
        );
        projecteurs.forEach(p->p.setColor(current));
    }

    private int calculate(int start, int end, double ratio) {
        return (int) (start + (end-start)*ratio);
    }

    public boolean isTerminated() {
        return position >= duration;
    }

    private int calculateDuration(Color start, Color end) {
        int max = Stream.of(end.getRed()-start.getRed(),
                end.getGreen()-start.getGreen(),
                end.getBlue()-start.getBlue(),
                end.getWhite()-start.getWhite())
                .map(Math::abs)
                .max(Integer::compareTo)
                .get();
        if (max == 0) {
            return 0;
        }
        return (int) Math.ceil(max/10d);
    }
}
