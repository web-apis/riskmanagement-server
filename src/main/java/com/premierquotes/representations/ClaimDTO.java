package com.premierquotes.representations;

import java.util.List;
import java.util.UUID;

import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.glassfish.jersey.linking.InjectLinks;
import org.glassfish.jersey.linking.InjectLink.Style;
import org.glassfish.jersey.server.Uri;

import com.premierquotes.models.Claim;
import com.premierquotes.models.Evidence;
import com.premierquotes.resources.ClaimManagement;

public class ClaimDTO {

	private final UUID id;

	private final String dateOfIncident;

	private final double amount;

	private final List<Evidence> evidence;

	public ClaimDTO(UUID id, String dateOfIncident, double amount, List<Evidence> evidence) {
		this.id = id;
		this.dateOfIncident = dateOfIncident;
		this.amount = amount;
		this.evidence = evidence;
	}

	public static ClaimDTO create(Claim claim) {
		return new ClaimDTO(claim.getId(), claim.getDateOfIncident().toString(), claim.getAmount(),
				claim.getEvidence());
	}

	@InjectLinks(@InjectLink(resource = ClaimManagement.class, method = "getClaimById", bindings = @Binding(name = "claimId", value = "${instance.id}"), style = Style.ABSOLUTE, rel = "self"))
	public List<Uri> links;

	public UUID getId() {
		return id;
	}

	public String getDateOfIncident() {
		return dateOfIncident;
	}

	public double getAmount() {
		return amount;
	}

	public List<Evidence> getEvidence() {
		return evidence;
	}

}
