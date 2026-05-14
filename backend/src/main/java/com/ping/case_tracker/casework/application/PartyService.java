package com.ping.case_tracker.casework.application;

import java.util.List;

import com.ping.case_tracker.casework.domain.exception.PartyNotFoundException;
import com.ping.case_tracker.casework.domain.model.enums.PartyRole;
import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.domain.repository.CaseParticipantRepository;
import com.ping.case_tracker.casework.domain.repository.PartyRepository;

import org.springframework.stereotype.Service;

@Service
public class PartyService {

    private final PartyRepository partyRepository;
    private final CaseParticipantRepository participantRepository;

    public PartyService(PartyRepository partyRepository, CaseParticipantRepository participantRepository) {
        this.partyRepository = partyRepository;
        this.participantRepository = participantRepository;
    }

    public Party createParty(String name, String email) {
        return partyRepository.save(new Party(null, name, email));
    }

    public Party updateParty(Long id, String name, String email) {
        findById(id);
        return partyRepository.update(new Party(id, name, email));
    }

    public void deleteParty(Long id) {
        partyRepository.deleteById(id);
    }

    public Party findById(Long id) {
        return partyRepository.findById(id)
            .orElseThrow(() -> new PartyNotFoundException(id));
    }

    public List<Party> findAll() {
        return partyRepository.findAll();
    }

    public CaseParticipant addParticipant(Long caseId, Long partyId, PartyRole role) {
        return participantRepository.save(new CaseParticipant(caseId, partyId, role));
    }

    public CaseParticipant updateParticipant(Long caseId, Long partyId, PartyRole role) {
        return participantRepository.update(new CaseParticipant(caseId, partyId, role));
    }

    public void removeParticipant(Long caseId, Long partyId) {
        participantRepository.deleteByCaseIdAndPartyId(caseId, partyId);
    }

    public List<CaseParticipant> findParticipants(Long caseId) {
        return participantRepository.findByCaseId(caseId);
    }
}
