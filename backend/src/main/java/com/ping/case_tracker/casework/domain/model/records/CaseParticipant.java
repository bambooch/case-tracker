package com.ping.case_tracker.casework.domain.model.records;

import com.ping.case_tracker.casework.domain.model.enums.PartyRole;

public record CaseParticipant(Long caseId, Long partyId, PartyRole role) {
}
