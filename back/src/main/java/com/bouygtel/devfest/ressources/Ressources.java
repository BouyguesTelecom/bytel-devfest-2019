package com.bouygtel.devfest.ressources;

import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.bouygtel.devfest.websocket.ActionExecuter;
import com.bouygtel.devfest.websocket.WebSocketClient;

/**
 * @author Arthur
 */
@Controller
public class Ressources {

	private static final Logger LOG = LoggerFactory.getLogger(Ressources.class);

	private static final AtomicInteger counter = new AtomicInteger(0);

	private WebSocketClient webSocketClient;

	public Ressources(WebSocketClient webSocketClient) {
		this.webSocketClient = webSocketClient;
	}

	@ActionExecuter(action = "RESET_COUNTER")
	public void resetCounter() {
		setCounter(0);
	}

	@ActionExecuter(action = "GET_COUNTER")
	public void getCounter() {
		sendCounter();
	}

	public void setCounter(int value) {
		counter.set(value);
		sendCounter();
	}

	public void incrementCounter() {
		counter.incrementAndGet();
		sendCounter();
	}

	private void sendCounter() {
		webSocketClient.sendMessage(Action.SET_COUNTER, counter);
	}

}
