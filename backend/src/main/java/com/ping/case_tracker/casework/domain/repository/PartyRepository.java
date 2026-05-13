package com.ping.case_tracker.casework.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Party;

public interface PartyRepository {

    Party save(Party party);

    Optional<Party> findById(Long id);

    List<Party> findAll();
}
