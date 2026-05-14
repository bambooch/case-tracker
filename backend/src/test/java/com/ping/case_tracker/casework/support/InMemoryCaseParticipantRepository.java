package com.ping.case_tracker.casework.support;

import java.util.ArrayList;
import java.util.List;

import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.repository.CaseParticipantRepository;

public class InMemoryCaseParticipantRepository implements CaseParticipantRepository {

    private final List<CaseParticipant> participants = new ArrayList<>();

    @Override
    public CaseParticipant save(CaseParticipant participant) {
        participants.removeIf(p -> p.caseId().equals(participant.caseId())
            && p.partyId().equals(participant.partyId()));
        participants.add(participant);
        return participant;
    }

    @Override
    public CaseParticipant update(CaseParticipant participant) {
        participants.removeIf(p -> p.caseId().equals(participant.caseId())
            && p.partyId().equals(participant.partyId()));
        participants.add(participant);
        return participant;
    }

    @Override
    public void deleteByCaseIdAndPartyId(Long caseId, Long partyId) {
        participants.removeIf(p -> p.caseId().equals(caseId) && p.partyId().equals(partyId));
    }

    @Override
    public List<CaseParticipant> findByCaseId(Long caseId) {
        return participants.stream().filter(p -> p.caseId().equals(caseId)).toList();
    }
}
