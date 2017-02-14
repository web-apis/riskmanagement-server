package org.irp.insurance.repositories;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.irp.insurance.models.Claim;

public class Claims {

	private Map<UUID, Claim> claims = new HashMap<>();

	/**
	 * For demonstration purposes, the constructor initializes with some
	 * randomly generated claims.
	 */
	public Claims() {
		Random random = new Random(8640);
		for (int year = 2010; year <= 2017; year++) {
			// Generate some claims for each year:
			for (int i = random.nextInt(5); i > 0; i--) {
				int month = random.nextInt(12) + 1;
				int day = random.nextInt(28) + 1;
				LocalDate date = LocalDate.of(year, month, day);
				Claim claim = new Claim(date, random.nextInt(10_000));
				claims.put(claim.getId(), claim);
			}
		}
	}

	public Claim create(LocalDate dateOfIncident, double amount) {
		Claim claim = new Claim(dateOfIncident, amount);
		claims.put(claim.getId(), claim);
		return claim;
	}

	public Optional<Claim> findById(UUID id) {
		return Optional.ofNullable(claims.get(id));
	}

	public List<Claim> list(int limit, int offset, String orderBy) {
		Comparator<Claim> ordering = Comparator.comparing(Claim::getDateOfIncident);

		if ("amount".equals(orderBy)) {
			ordering = Comparator.comparing(Claim::getAmount);
		}

		return claims.values().stream().sorted(ordering).skip(offset).limit(limit).collect(Collectors.<Claim>toList());
	}

	public int getSize() {
		return claims.size();
	}

	public boolean update(Claim claim) {
		if (!claims.containsKey(claim.getId())) {
			return false;
		}
		claims.put(claim.getId(), claim);
		return true;
	}

	public Map<Integer, List<Claim>> listByYear(int startYear, int numberOfYears) {
		return claims.values().stream().filter(claim -> {
			int yearOfClaim = getYearOfClaim(claim);
			return yearOfClaim >= startYear && yearOfClaim < startYear + numberOfYears;
		}).sorted(Comparator.comparing(Claim::getDateOfIncident))
				.collect(Collectors.groupingBy(this::getYearOfClaim, LinkedHashMap::new, Collectors.toList()));
	}

	private int getYearOfClaim(Claim claim) {
		return claim.getDateOfIncident().getYear();
	}
}
