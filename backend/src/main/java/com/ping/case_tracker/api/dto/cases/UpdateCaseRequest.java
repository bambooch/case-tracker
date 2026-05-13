package com.ping.case_tracker.api.dto.cases;

import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateCaseRequest(@NotBlank String title, @NotNull CaseStatus status) {
}
