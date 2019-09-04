package com.bouygtel.devfest.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.bouygtel.devfest.websocket.WebSocketClient;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		// On ouvre le WS sur bytel-ws
		registry.addHandler(new WebSocketClient(), "/bytel-ws").setAllowedOrigins("*");
	}

}