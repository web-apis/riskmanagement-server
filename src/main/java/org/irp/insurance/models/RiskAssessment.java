package org.irp.insurance.models;

public class RiskAssessment {

	private final int year;
	
	private final int policyCount;

	private final double totalClaimValue;

	private final int claimCount;

	private final double totalInsuredValue;

	public RiskAssessment(int year, int policyCount, double totalClaimValue, int claimCount, double totalInsuredValue) {
		this.year = year;
		this.policyCount = policyCount;
		this.totalClaimValue = totalClaimValue;
		this.claimCount = claimCount;
		this.totalInsuredValue = totalInsuredValue;
	}

	public double getTotalClaimValue() {
		return totalClaimValue;
	}

	public int getClaimCount() {
		return claimCount;
	}

	public int getPolicyCount() {
		return policyCount;
	}

	public double getTotalInsuredValue() {
		return totalInsuredValue;
	}

	public int getYear() {
		return year;
	}
}
