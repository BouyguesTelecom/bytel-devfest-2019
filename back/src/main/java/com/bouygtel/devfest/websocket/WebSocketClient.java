package com.bouygtel.devfest.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Client WebSocket pour communiquer avec le front
 */
public class WebSocketClient implements WebSocketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(WebSocketClient.class);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.info("Reception d'une connexion !");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		LOG.info("Reception d'un msg !");
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
		LOG.info("onError::", exception);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		LOG.info("afterConnectionClosed::{}", closeStatus);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}
}
