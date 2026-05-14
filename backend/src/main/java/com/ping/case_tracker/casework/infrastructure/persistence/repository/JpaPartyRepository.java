package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.domain.repository.PartyRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.entity.PartyEntity;

import org.springframework.stereotype.Repository;

@Repository
public class JpaPartyRepository implements PartyRepository {

    private final SpringDataPartyJpaRepository repository;

    public JpaPartyRepository(SpringDataPartyJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Party save(Party party) {
        PartyEntity saved = repository.save(new PartyEntity(null, party.name(), party.email()));
        return toDomain(saved);
    }

    @Override
    public Party update(Party party) {
        PartyEntity saved = repository.save(new PartyEntity(party.id(), party.name(), party.email()));
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Party> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Party> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    private Party toDomain(PartyEntity entity) {
        return new Party(entity.getId(), entity.getName(), entity.getEmail());
    }
}
