package com.ping.case_tracker.casework.domain.repository;

import java.util.List;

import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;

public interface CaseParticipantRepository {

    CaseParticipant save(CaseParticipant participant);

    void deleteByCaseIdAndPartyId(Long caseId, Long partyId);

    List<CaseParticipant> findByCaseId(Long caseId);
}
