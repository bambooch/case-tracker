package com.ping.case_tracker;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseController {

	@GetMapping("/api/cases")
	public List<Map<String, Object>> getCases(@RequestParam(required = false) Integer limit) {
        List<Map<String, Object>> cases = List.of(
            Map.of("id", 1, "title", "Missing documents", "status", "OPEN"),
            Map.of("id", 2, "title", "Payment dispute", "status", "IN_REVIEW"),
            Map.of("id", 3, "title", "Policy update", "status", "CLOSED")
        );



		return cases;
	}
}