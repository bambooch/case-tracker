package com.ping.case_tracker.api.dto.note;

import jakarta.validation.constraints.NotBlank;

public record UpdateNoteRequest(@NotBlank String content) {
}
