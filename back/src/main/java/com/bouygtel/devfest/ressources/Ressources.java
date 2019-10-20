package com.bouygtel.devfest.ressources;

import java.time.Instant;

import com.bouygtel.devfest.light.LightController;
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

	private static final Logger LOGGER = LoggerFactory.getLogger(Ressources.class);

	private final Stats stats;

	private WebSocketClient webSocketClient;

	private LightController lightController;

	private long lastSendStat = 0;

	public Ressources(WebSocketClient webSocketClient, LightController lightController) {
		this.webSocketClient = webSocketClient;
		this.lightController = lightController;
		stats = new Stats();
	}

	@ActionExecuter(action = "RESET_STATS")
	public void resetCounter() {
		stats.reset();
	}

	@ActionExecuter(action = "GET_STATS")
	public void getCounter() {
		sendStats();
	}

	public void addHit() {
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
		lightController.changeValue(stats.getMeanSpeed());
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
