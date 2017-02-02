package com.premierquotes.repositories;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.premierquotes.models.Claim;

public class Claims {

	private Map<UUID, Claim> claims = new HashMap<>();

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
