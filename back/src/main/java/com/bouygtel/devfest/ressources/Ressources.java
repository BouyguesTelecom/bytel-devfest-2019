package com.bouygtel.devfest.ressources;

import java.time.Instant;

import org.springframework.stereotype.Controller;

import com.bouygtel.devfest.websocket.ActionExecuter;
import com.bouygtel.devfest.websocket.WebSocketClient;

/**
 * @author Arthur
 */
@Controller
public class Ressources {

	private final Stats stats;

	private WebSocketClient webSocketClient;

	private long lastSendStat = 0;

	public Ressources(WebSocketClient webSocketClient) {
		this.webSocketClient = webSocketClient;
		stats = new Stats();
	}

	@ActionExecuter(action = "RESET_COUNTER")
	public void resetCounter() {
		stats.reset();
	}

	@ActionExecuter(action = "GET_STATS")
	public void getCounter() {
		sendStats();
	}

	public synchronized void addHit() {
		stats.addHit(Instant.now());
		sendStats();
	}

	public void sendStats() {
		// Antispam pour éviter de faire lager le front
		if (System.currentTimeMillis() - lastSendStat < 50) {
			return;
		}
		lastSendStat = System.currentTimeMillis();

		// Mise à jour des stats
		stats.update();
		webSocketClient.sendMessage(Action.SET_STATS, stats);
	}

	public void startRequest(int velocity) {
		webSocketClient.sendMessage(Action.REQUEST_START, velocity);
	}

	public void endRequest(int velocity) {
		webSocketClient.sendMessage(Action.REQUEST_END, velocity);
	}

	public Stats getStats() {
		return stats;
	}
}
