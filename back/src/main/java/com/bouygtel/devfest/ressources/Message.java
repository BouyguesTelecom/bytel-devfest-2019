package com.bouygtel.devfest.ressources;

public class Message {

	private Action action;
	private Object value;

	private Message() {
		// Mapping
	}

	public Message(Action action, Object value) {
		this.action = action;
		this.value = value;
	}

	public Message(Action action) {
		this(action, null);
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}
}
