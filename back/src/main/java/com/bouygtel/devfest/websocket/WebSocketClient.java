package com.bouygtel.devfest.websocket;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Client WebSocket pour communiquer avec le front
 */
public class WebSocketClient implements WebSocketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(WebSocketClient.class);

	private static AtomicInteger compteur = new AtomicInteger(0);

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.info("Reception d'une connexion !");
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		LOG.info("Reception du message: {}", message.getPayload());
		ObjectMapper mapper = new ObjectMapper();
		Message m = mapper.readValue((String) message.getPayload(), Message.class);
		if (m.getAction().equals("resetCounter")) {
			compteur.set(0);
		} else {
			compteur.incrementAndGet();
		}
		LOG.debug("Envoi du compteur: {}", compteur);
		session.sendMessage(new TextMessage("{\"action\": \"setCounter\",\"value\": " + compteur + "}"));
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
