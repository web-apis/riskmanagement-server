package org.irp.insurance.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.irp.insurance.models.Claim;
import org.irp.insurance.models.RiskAssessment;
import org.irp.insurance.repositories.Claims;

@Path("/riskreport")
@Produces(value = MediaType.APPLICATION_JSON)
public class RiskReport {

	private final Claims claims;

	public RiskReport(Claims claims) {
		this.claims = claims;
	}

	@GET
	public List<RiskAssessment> getRiskReport(@QueryParam("firstYear") @NotNull @Valid Integer firstYear,
			@QueryParam("noOfYears") @NotNull @Valid Integer noOfYears) {

		int policyCount = 42; // we just make some values up

		final double totalInsuredValue = 1E6;

		return claims.listByYear(firstYear, noOfYears).values().stream().map(claims -> {
			double totalClaimValue = claims.stream().mapToDouble(Claim::getAmount).sum();
			int claimCount = claims.size();
			return new RiskAssessment(claims.get(0).getDateOfIncident().getYear(), policyCount, totalClaimValue,
					claimCount, totalInsuredValue);
		}).collect(Collectors.toList());
	}
}