package com.bouygtel.devfest.ressources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Stats {

	private static final Logger LOGGER = LoggerFactory.getLogger(Stats.class);

	private Long speed;
	private Long maxSpeed;

	private double meanSpeed;
	private double maxMeanSpeed;

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
		hits = Collections.synchronizedList(new ArrayList<>());
		session = new Session();
	}

	public void startSession() {
		session.setActive(true);
		session.setMaxSpeed(0L);
		session.setMaxMeanSpeed(0L);
		session.setLambdasUtilisees(0);
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
		hits = Collections.synchronizedList(new ArrayList<>());
		maxSpeed = 0L;
		maxMeanSpeed = 0;
		maxLambdasUtilisees = 0;
		lambdasUtilisees = 0;
		session = new Session();
	}

	public synchronized Long update() {
		cleanHits();

		// Calcul de la vitesse instantanée
		setSpeed(hits.stream()//
				.dropWhile(instant -> Instant.now().minusSeconds(1).isAfter(instant))//
				.count());

		// Calcul de la vitesse moyenne sur les X dernieres secondes
		setMeanSpeed(hits.stream()//
				.dropWhile(instant -> Instant.now().minusSeconds(meanTime).isAfter(instant))//
				.count() / (double) meanTime);

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
		if (lambdasUtilisees > session.getLambdasUtilisees()) {
			session.setLambdasUtilisees(lambdasUtilisees);
		}

		return speed;
	}

	private void cleanHits() {
		// Suppression des hits trop anciens
		if (hits.size() > 800 && hits.get(0).isBefore(Instant.now().minusSeconds(40))) {
			LOGGER.info("Suppression des hits trop anciens");
			Instant outdatedThreshold = Instant.now().minusSeconds(30);
			hits.removeIf(hit -> hit.isBefore(outdatedThreshold));
		}
	}

	/**
	 * La vitesse est à jour seulement après un update des stats
	 */
	public Long getSpeed() {
		return speed;
	}

	public void setSpeed(Long speed) {
		this.speed = speed;

		if (speed > maxSpeed) {
			maxSpeed = speed;
		}
	}

	public Long getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Long maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public double getMeanSpeed() {
		return meanSpeed;
	}

	public void setMeanSpeed(double meanSpeed) {
		this.meanSpeed = meanSpeed;

		if (meanSpeed > maxMeanSpeed) {
			maxMeanSpeed = meanSpeed;
		}
	}

	public double getMaxMeanSpeed() {
		return maxMeanSpeed;
	}

	public List<Instant> getHits() {
		return hits;
	}

	public Session getSession() {
		return session;
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

		if (lambdasUtilisees > maxLambdasUtilisees) {
			maxLambdasUtilisees = lambdasUtilisees;
		}
	}

}