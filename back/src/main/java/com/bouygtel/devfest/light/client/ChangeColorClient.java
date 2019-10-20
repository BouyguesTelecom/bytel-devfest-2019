package com.bouygtel.devfest.light.client;

import com.bouygtel.devfest.light.client.in.ColorIn;
import org.springframework.web.client.RestTemplate;

public class ChangeColorClient {

    private final RestTemplate restTemplate;

    private final String url;

    public ChangeColorClient(String url) {
        this.url = url;
        restTemplate = new RestTemplate();
    }

    public void changeColor(ColorIn color) {
        restTemplate.postForObject(url, color, Void.class);
    }
}
