package com.ping.case_tracker.casework.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Case;

public interface CaseRepository {

    Case save(Case c);

    Case update(Case c);

    void deleteById(Long id);

    List<Case> findAll();

    Optional<Case> findById(Long id);
}
