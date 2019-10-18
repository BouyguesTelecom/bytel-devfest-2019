package com.bouygtel.devfest.ressources;

import java.util.UUID;

public class Session {

	private boolean isActive = false;
	private Long maxSpeed = 0L;
	private String uiidSession = UUID.randomUUID().toString(); // pour ne pas avoir à faire un constructeur, on
																// n'instancie cette classe qu'une fois

	// Le nb de lambdas utilisées pour cette session
	private int lambdasUtilisees = 0;

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Long getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(Long maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public String getUiidSession() {
		return uiidSession;
	}

	public void setUiidSession(String uiidSession) {
		this.uiidSession = uiidSession;
	}

	public int getLambdasUtilisees() {
		return lambdasUtilisees;
	}

	public void setLambdasUtilisees(int lambdasUtilisees) {
		this.lambdasUtilisees = lambdasUtilisees;
	}
}
