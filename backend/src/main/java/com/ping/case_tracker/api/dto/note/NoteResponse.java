package com.ping.case_tracker.api.dto.note;

import java.time.Instant;

public record NoteResponse(Long id, Long caseId, String content, String author, Instant createdAt) {
}
