package org.irp.insurance.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Claim {

	private final UUID id;

	private final LocalDate dateOfIncident;

	private final double amount;

	private final List<Evidence> evidence;

	public Claim(LocalDate dateOfIncident, double amount) {
		this.dateOfIncident = dateOfIncident;
		this.amount = amount;
		this.id = UUID.randomUUID();
		this.evidence = new ArrayList<>();
	}

	public UUID getId() {
		return id;
	}

	public LocalDate getDateOfIncident() {
		return dateOfIncident;
	}

	public double getAmount() {
		return amount;
	}

	public List<Evidence> getEvidence() {
		return evidence;
	}

	public void addEvidence(String evidence) {
		this.evidence.add(new Evidence(evidence));
	}

	public void deleteEvidenceById(UUID evidenceId) {
		this.evidence.removeIf(e -> e.getId().equals(evidenceId));
	}
}