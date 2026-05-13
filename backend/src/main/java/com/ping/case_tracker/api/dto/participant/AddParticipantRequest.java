package com.ping.case_tracker.api.dto.participant;

import com.ping.case_tracker.casework.domain.model.enums.PartyRole;

import jakarta.validation.constraints.NotNull;

public record AddParticipantRequest(@NotNull Long partyId, @NotNull PartyRole role) {
}
