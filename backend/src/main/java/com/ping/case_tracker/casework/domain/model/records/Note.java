package com.ping.case_tracker.casework.domain.model.records;

import java.time.Instant;

public record Note(Long id, Long caseId, String content, String author, Instant createdAt) {
}
