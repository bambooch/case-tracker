package com.ping.case_tracker.casework.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.domain.repository.PartyRepository;

public class InMemoryPartyRepository implements PartyRepository {

    private final List<Party> parties = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Party save(Party party) {
        Party saved = new Party(idSequence.getAndIncrement(), party.name(), party.email());
        parties.add(saved);
        return saved;
    }

    @Override
    public Party update(Party party) {
        parties.removeIf(p -> p.id().equals(party.id()));
        parties.add(party);
        return party;
    }

    @Override
    public void deleteById(Long id) {
        parties.removeIf(p -> p.id().equals(id));
    }

    @Override
    public Optional<Party> findById(Long id) {
        return parties.stream().filter(p -> p.id().equals(id)).findFirst();
    }

    @Override
    public List<Party> findAll() {
        return parties;
    }
}
