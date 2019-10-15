package com.bouygtel.devfest.ressources;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Stats {

	private Long speed;
	private Long maxSpeed;
	private List<Instant> hits;

	public Stats() {
		speed = 0L;
		maxSpeed = 0L;
		hits = new ArrayList<>();
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

	public List<Instant> getHits() {
		return hits;
	}

	public void addHit(Instant hit) {
		hits.add(hit);
	}

	public void reset() {
		hits = Collections.emptyList();
		maxSpeed = 0L;
	}

	public Long update() {
		speed = hits.stream()//
				.dropWhile(instant -> Instant.now().minusSeconds(1).isAfter(instant))//
				.collect(Collectors.counting());

		if (speed > maxSpeed) {
			maxSpeed = speed;
		}
		return speed;
	}

}
