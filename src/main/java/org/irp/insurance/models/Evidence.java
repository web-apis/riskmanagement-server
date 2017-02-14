package org.irp.insurance.models;

import java.util.UUID;

public class Evidence {

	private final UUID id;

	private final String description;

	public Evidence(String description) {
		this.id = UUID.randomUUID();
		this.description = description;
	}

	public UUID getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	// TODO could add image to make the sample more interesting
}
