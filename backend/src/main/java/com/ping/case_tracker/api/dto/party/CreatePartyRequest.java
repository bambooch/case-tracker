package com.ping.case_tracker.api.dto.party;

import jakarta.validation.constraints.NotBlank;

public record CreatePartyRequest(@NotBlank String name, String email) {
}
