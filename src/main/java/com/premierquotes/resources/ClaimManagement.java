package com.premierquotes.resources;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.premierquotes.models.Claim;
import com.premierquotes.repositories.Claims;
import com.premierquotes.representations.ClaimDTO;
import com.premierquotes.representations.ClaimsDTO;

@Path("/claims")
@Produces(value = MediaType.APPLICATION_JSON)
public class ClaimManagement {

	public static class OpenClaimParameters {

		@JsonProperty("dateOfIncident")
		public String dateOfIncident;

		@JsonProperty("amount")
		public double amount;
	}

	public static class AddEvidenceParameters {

		@JsonProperty("evidence")
		public String evidence;
	}

	private final Claims claims;

	public ClaimManagement(Claims claims) {
		this.claims = claims;
	}

	// curl http://localhost:8080/claims -H "Content-Type: application/json" -d
	// '{"dateOfIncident":"2007-12-03T10:15:30", "amount":123 }'
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public ClaimDTO openClaim(@NotNull @Valid OpenClaimParameters params) {
		return ClaimDTO.create(claims.create(LocalDate.parse(params.dateOfIncident), params.amount));
	}

	// curl http://localhost:8080/claims\?limit\=10\&offset\=0
	@GET
	public ClaimsDTO listClaims(@DefaultValue("3") @QueryParam("limit") Integer limit,
			@DefaultValue("0") @QueryParam("offset") Integer offset, @QueryParam("orderBy") String orderBy) {
		List<ClaimDTO> result = claims.list(limit, offset, orderBy).stream().map(ClaimDTO::create)
				.collect(Collectors.toList());
		return new ClaimsDTO(limit, offset, claims.getSize(), orderBy, result);
	}

	@GET
	@Path("/{claimId}")
	public ClaimDTO getClaimById(@PathParam("claimId") UUID claimId) {
		return claims.findById(claimId).map(ClaimDTO::create).orElseThrow(noSuchClaim);
	}

	@PUT
	@Path("/{claimId}")
	public ClaimDTO updateClaim(@PathParam("claimId") UUID claimId, @NotNull @Valid Claim claim) {
		boolean result = claims.update(claim);
		if (!result) {
			throw noSuchClaim.get();
		}
		return ClaimDTO.create(claim);
	}

	@POST
	@Path("/{claimId}/evidence")
	public ClaimDTO addEvidenceToClaim(@PathParam("claimId") UUID claimId,
			@NotNull @Valid AddEvidenceParameters params) {
		Claim claim = claims.findById(claimId).orElseThrow(noSuchClaim);
		claim.addEvidence(params.evidence);
		return ClaimDTO.create(claim);
	}

	@DELETE
	@Path("/{claimId}/evidence/{evidenceId}")
	public ClaimDTO deleteEvidenceFromClaim(@PathParam("claimId") UUID claimId,
			@PathParam("evidenceId") UUID evidenceId) {
		Claim claim = claims.findById(claimId).orElseThrow(noSuchClaim);
		// TODO check if valid evidenceId
		claim.deleteEvidenceById(evidenceId);
		return ClaimDTO.create(claim);
	}

	private final Supplier<WebApplicationException> noSuchClaim = () -> new WebApplicationException("No such claim",
			Status.NOT_FOUND);

}
