package com.ping.case_tracker.api.dto.party;

import jakarta.validation.constraints.NotBlank;

public record UpdatePartyRequest(@NotBlank String name, String email) {
}
