package com.ping.case_tracker.casework.persistence;

import java.util.List;

import com.ping.case_tracker.casework.CaseRecord;
import com.ping.case_tracker.casework.CaseRepository;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class JpaCaseRepository implements CaseRepository {

    private final SpringDataCaseJpaRepository repository;

    public JpaCaseRepository(SpringDataCaseJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public CaseRecord save(CaseRecord caseRecord) {
        CaseEntity savedCase = repository.save(new CaseEntity(null, caseRecord.title(), caseRecord.status()));
        return new CaseRecord(savedCase.getId(), savedCase.getTitle(), savedCase.getStatus());
    }

    @Override
    public List<CaseRecord> findAll() {
        return repository.findAll().stream()
            .map(caseEntity -> new CaseRecord(caseEntity.getId(), caseEntity.getTitle(), caseEntity.getStatus()))
            .toList();
    }
}