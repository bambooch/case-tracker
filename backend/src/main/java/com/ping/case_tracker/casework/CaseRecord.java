package com.ping.case_tracker.casework;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CaseRecord(Long id, @NotBlank String title, @NotNull CaseStatus status) {
}