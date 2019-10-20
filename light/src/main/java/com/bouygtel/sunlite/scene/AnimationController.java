package com.bouygtel.sunlite.scene;

import com.bouygtel.sunlite.scene.animations.FadeAnimation;
import com.bouygtel.sunlite.scene.entities.Projecteur;
import com.bouygtel.sunlite.scene.values.Color;
import com.bouygtel.sunlite.usb.SunliteController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class AnimationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AnimationController.class);

    private SunliteController sunliteController;

    private List<Projecteur> projecteurs;

    private List<FadeAnimation> animations = new LinkedList<>();

    private Color targetColor = null;

    public void setTargetColor(Color targetColor) {
        this.targetColor = targetColor;
    }

    public AnimationController(SunliteController sunliteController) {
        this.sunliteController = sunliteController;
        this.projecteurs = Arrays.asList(new Projecteur(0));
    }

    @Scheduled(fixedRate = 20)
    private void animate() {
        if (targetColor != null) {
            animations.clear();
            animations.add(new FadeAnimation(projecteurs.get(0).getColor(), targetColor, projecteurs, 10));
            targetColor = null;
        }

        animations.forEach(FadeAnimation::update);

        if (!animations.isEmpty()) {
            projecteurs.forEach(p -> p.updateValues(sunliteController));
            sunliteController.send();
        }

        animations.removeIf(FadeAnimation::isTerminated);
    }

}
