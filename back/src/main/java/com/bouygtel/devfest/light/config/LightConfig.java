package com.bouygtel.devfest.light.config;

import com.bouygtel.devfest.light.ColorGradient;
import com.bouygtel.devfest.light.LightController;
import com.bouygtel.devfest.light.client.ChangeColorClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LightConfig {

    @Bean
    public ChangeColorClient changeColorClient(@Value("${light.url}") String url) {
        return new ChangeColorClient(url);
    }

    @Bean
    public LightController lightController(ChangeColorClient changeColorClient,
                           @Value("${light.gradient}") String colorGradient,
                           @Value("${light.active}") boolean active) {
        return new LightController(changeColorClient, ColorGradient.parse(colorGradient), active);
    }
}
