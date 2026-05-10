package com.ping.case_tracker;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseController {
    List<Map<String, Object>> cases = List.of(
        Map.of("id", 1, "title", "Missing documents", "status", "OPEN"),
        Map.of("id", 2, "title", "Payment dispute", "status", "IN_REVIEW"),
        Map.of("id", 3, "title", "Policy update", "status", "CLOSED"),
        Map.of("id", 4, "title", "Claim processing delay", "status", "OPEN"),
        Map.of("id", 5, "title", "Incorrect billing", "status", "IN_REVIEW"),
        Map.of("id", 6, "title", "Coverage inquiry", "status", "CLOSED"),
        Map.of("id", 7, "title", "Fraud investigation", "status", "OPEN"),
        Map.of("id", 8, "title", "Customer feedback", "status", "IN_REVIEW"),
        Map.of("id", 9, "title", "Policy cancellation", "status", "CLOSED")
    );

	@GetMapping("/api/cases")
	public List<Map<String, Object>> getCases(@RequestParam(required = false) Integer limit) {
        int safeLimit = (limit == null) ? cases.size() : Math.min(limit, cases.size());
		return cases.subList(0, safeLimit);
	}
}