package com.ping.case_tracker.api.dto.cases;

public record CaseSummaryResponse(Long id, String title, String status, String attentionLevel) {
}
