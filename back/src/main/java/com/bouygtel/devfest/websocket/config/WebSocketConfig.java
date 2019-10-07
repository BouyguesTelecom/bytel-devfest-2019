package com.bouygtel.devfest.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;

import com.bouygtel.devfest.websocket.MessageHandler;
import com.bouygtel.devfest.websocket.WebSocketClient;

@Configuration
@EnableWebSocket
public class WebSocketConfig {

	@Bean
	public WebSocketClient createWebSocketClient(MessageHandler messageHandler) {
		return new WebSocketClient(messageHandler);
	}

	@Bean
	public WebSocketConfigurer createWebSocketConfigurer(WebSocketClient webSocketClient) {
		return registry -> registry.addHandler(webSocketClient, "/bytel-ws").setAllowedOrigins("*");
	}

}