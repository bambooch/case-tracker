package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import java.util.List;

import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.repository.CaseParticipantRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.entity.CaseParticipantEntity;

import org.springframework.stereotype.Repository;

@Repository
public class JpaCaseParticipantRepository implements CaseParticipantRepository {

    private final SpringDataParticipantJpaRepository repository;

    public JpaCaseParticipantRepository(SpringDataParticipantJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CaseParticipant save(CaseParticipant participant) {
        CaseParticipantEntity saved = repository.save(
            new CaseParticipantEntity(participant.caseId(), participant.partyId(), participant.role()));
        return toDomain(saved);
    }

    @Override
    public void deleteByCaseIdAndPartyId(Long caseId, Long partyId) {
        repository.deleteById(new CaseParticipantEntity.CaseParticipantId(caseId, partyId));
    }

    @Override
    public List<CaseParticipant> findByCaseId(Long caseId) {
        return repository.findByIdCaseId(caseId).stream().map(this::toDomain).toList();
    }

    private CaseParticipant toDomain(CaseParticipantEntity entity) {
        return new CaseParticipant(entity.getCaseId(), entity.getPartyId(), entity.getRole());
    }
}
