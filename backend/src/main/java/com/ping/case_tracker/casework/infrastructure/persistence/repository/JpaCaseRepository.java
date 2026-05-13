package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.repository.CaseRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.entity.CaseEntity;

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
    public Case save(Case c) {
        CaseEntity saved = repository.save(new CaseEntity(null, c.title(), c.status()));
        return toDomain(saved);
    }

    @Override
    public Case update(Case c) {
        CaseEntity saved = repository.save(new CaseEntity(c.id(), c.title(), c.status()));
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<Case> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Case> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    private Case toDomain(CaseEntity entity) {
        return new Case(entity.getId(), entity.getTitle(), entity.getStatus());
    }
}
