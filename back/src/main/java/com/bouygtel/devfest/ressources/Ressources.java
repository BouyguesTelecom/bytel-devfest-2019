package com.bouygtel.devfest.ressources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

	private static List<Instant> frappes = new ArrayList<>();
	private static Long maxSpeed = 0L;

	private WebSocketClient webSocketClient;

	public Ressources(WebSocketClient webSocketClient) {
		this.webSocketClient = webSocketClient;
	}

	@ActionExecuter(action = "RESET_COUNTER")
	public void resetCounter() {
		frappes = Collections.emptyList();
	}

	@ActionExecuter(action = "GET_SPEED")
	public void getCounter() {
		sendSpeed();
	}

	public synchronized void ajouterFrappe() {
		frappes.add(Instant.now());
		sendSpeed();
	}

	public void sendSpeed() {
		Long speed = frappes.stream()//
				.filter(instant -> Instant.now().minusSeconds(1).isBefore(instant))//
				.collect(Collectors.counting());
		if (speed > maxSpeed)
			maxSpeed = speed;
		LOG.info("speed : {}", speed);
		webSocketClient.sendMessage(Action.SET_SPEED, speed);
	}

	public void startRequete(UUID id) {
		webSocketClient.sendMessage(Action.REQUEST_START, id);
	}

	public void endRequete(UUID id) {
		webSocketClient.sendMessage(Action.REQUEST_END, id);
	}
}
