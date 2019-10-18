package com.bouygtel.devfest.aws;

public class LambdaIn {

	private final String uuid;

	public LambdaIn(String uuid) {
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}
}