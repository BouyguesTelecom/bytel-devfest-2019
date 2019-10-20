package com.bouygtel.sunlite.services;

import com.bouygtel.sunlite.scene.AnimationController;
import com.bouygtel.sunlite.scene.values.Color;
import com.bouygtel.sunlite.services.in.ColorIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColorService {

    @Autowired
    private AnimationController animationController;

    @RequestMapping(path = "/color", method = RequestMethod.POST)
    public void changeColor(@RequestBody ColorIn color) {
        animationController.setTargetColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getWhite()));
    }

}
