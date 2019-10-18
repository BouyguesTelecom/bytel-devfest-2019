package com.bouygtel.devfest.midi;

public class CustomMidiMessage {

	private Long timestamp;
	private int velocity;

	public CustomMidiMessage(Long timestamp, int velocity) {
		super();
		this.timestamp = timestamp;
		this.velocity = velocity;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public int getVelocity() {
		return velocity;
	}

	public void setVelocity(int velocity) {
		this.velocity = velocity;
	}

}
