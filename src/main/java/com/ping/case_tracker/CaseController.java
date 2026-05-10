package com.ping.case_tracker;

import java.util.List;

import com.ping.case_tracker.casework.CaseCatalogService;
import com.ping.case_tracker.casework.CaseStatus;
import com.ping.case_tracker.casework.CaseSummary;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CaseController {
    private final CaseCatalogService caseCatalogService;

    public CaseController(CaseCatalogService caseCatalogService) {
        this.caseCatalogService = caseCatalogService;
    }

	@GetMapping("/api/cases")
	public List<CaseSummary> getCases(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) CaseStatus status) {
		return caseCatalogService.findCases(limit, status);
	}
}