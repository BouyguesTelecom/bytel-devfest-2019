package com.bouygtel.devfest.ressources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Stats {

	private static final Logger LOGGER = LoggerFactory.getLogger(Stats.class);

	private Long speed;
	private Long maxSpeed;

	private Long meanSpeed;
	private Long maxMeanSpeed;

	private int meanTime = 10;

	@JsonIgnore
	private List<Instant> hits;
	private Session session;
	private int lambdasUtilisees; // on aura le nb de lambda de la session en cours / terminée
	private int maxLambdasUtilisees = 0;

	public Stats() {
		speed = 0L;
		maxSpeed = 0L;
		meanSpeed = 0L;
		maxMeanSpeed = 0L;
		hits = new ArrayList<>();
		session = new Session();
	}

	/**
	 * La vitesse est à jour seulement après un update des stats
	 */
	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;
	}

	public Long getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Long maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public Long getMeanSpeed() {
		return meanSpeed;
	}

	public Long getMaxMeanSpeed() {
		return maxMeanSpeed;
	}

	public List<Instant> getHits() {
		return hits;
	}

	public Session getSession() {
		return session;
	}

	public void startSession() {
		session.setActive(true);
		session.setMaxSpeed(0L);
	}

	public void closeSession() {
		if (session.isActive()) {
			session.setActive(false);
			session.setUiidSession(UUID.randomUUID().toString());
		}
	}

	public void addHit(Instant hit) {
		hits.add(hit);
	}

	public void reset() {
		hits = Collections.emptyList();
		maxSpeed = 0L;
		maxLambdasUtilisees = 0;
	}

	public synchronized Long update() {
		cleanHits();

		// Calcul de la vitesse instantanée
		speed = hits.stream()//
				.dropWhile(instant -> Instant.now().minusSeconds(1).isAfter(instant))//
				.count();

		if (speed > maxSpeed) {
			maxSpeed = speed;
		}

		// Calcul de la vitesse moyenne sur les X dernieres secondes
		meanSpeed = hits.stream()//
				.dropWhile(instant -> Instant.now().minusSeconds(meanTime).isAfter(instant))//
				.count()/meanTime;

		if (meanSpeed > maxMeanSpeed) {
			maxMeanSpeed = meanSpeed;
		}

		// Gestion des sessions
		if (speed > 0 && !session.isActive()) {
			startSession();
		} else if (hits.stream()//
				.noneMatch(instant -> Instant.now().minusSeconds(10).isBefore(instant))) {
			closeSession();
		}

		// Mise à jour de la session
		if (speed > session.getMaxSpeed()) {
			session.setMaxSpeed(speed);
		}
		if (meanSpeed > session.getMaxMeanSpeed()) {
			session.setMaxMeanSpeed(meanSpeed);
		}

		if (session.getLambdasUtilisees() > maxLambdasUtilisees) {
			maxLambdasUtilisees = session.getLambdasUtilisees();
		}

		return speed;
	}

	public int getMaxLambdasUtilisees() {
		return maxLambdasUtilisees;
	}

	public void setMaxLambdasUtilisees(int maxLambdasUtilisees) {
		this.maxLambdasUtilisees = maxLambdasUtilisees;
	}

	public int getLambdasUtilisees() {
		return lambdasUtilisees;
	}

	public void setLambdasUtilisees(int lambdasUtilisees) {
		this.lambdasUtilisees = lambdasUtilisees;
	}


	private void cleanHits() {
		// Suppression des hits trop anciens
		if (hits.size() > 800 && hits.get(0).isBefore(Instant.now().minusSeconds(40))) {
			LOGGER.info("Suppression des hits trop anciens");
			Instant outdatedThreshold = Instant.now().minusSeconds(30);
			hits.removeIf(hit -> hit.isBefore(outdatedThreshold));
		}
	}
}