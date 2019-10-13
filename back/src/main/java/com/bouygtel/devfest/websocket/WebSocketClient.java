package com.bouygtel.devfest.websocket;

import java.io.IOException;
import java.io.UncheckedIOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.bouygtel.devfest.ressources.Action;
import com.bouygtel.devfest.ressources.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Client WebSocket pour communiquer avec le front
 */
public class WebSocketClient implements WebSocketHandler {
	private static final Logger LOG = LoggerFactory.getLogger(WebSocketClient.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static WebSocketSession webSocketSession = null;
	private MessageHandler messageHandler;

	public WebSocketClient(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}

	public synchronized void sendMessage(Message message) {
		if (webSocketSession != null) {
			try {
				webSocketSession.sendMessage(new TextMessage(MAPPER.writeValueAsBytes(message)));
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}
	}

	public void sendMessage(Action action, Object value) {
		sendMessage(new Message(action, value));
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		LOG.info("Reception d'une connexion !");
		webSocketSession = session;

	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> messageIn) throws Exception {
//		LOG.info("Reception du message: {}", messageIn.getPayload());
		Message message = MAPPER.readValue((String) messageIn.getPayload(), Message.class);
		messageHandler.handleMessage(message);
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
