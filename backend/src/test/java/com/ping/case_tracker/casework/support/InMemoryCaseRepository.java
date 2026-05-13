package com.ping.case_tracker.casework.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.repository.CaseRepository;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;

public class InMemoryCaseRepository implements CaseRepository {

    private final List<Case> cases = new ArrayList<>(List.of(
        new Case(1L, "Missing documents", CaseStatus.OPEN),
        new Case(2L, "Payment dispute", CaseStatus.IN_REVIEW),
        new Case(3L, "Policy update", CaseStatus.CLOSED),
        new Case(4L, "Claim processing delay", CaseStatus.OPEN),
        new Case(5L, "Incorrect billing", CaseStatus.IN_REVIEW),
        new Case(6L, "Coverage inquiry", CaseStatus.CLOSED),
        new Case(7L, "Fraud investigation", CaseStatus.OPEN),
        new Case(8L, "Customer feedback", CaseStatus.IN_REVIEW),
        new Case(9L, "Policy cancellation", CaseStatus.CLOSED)
    ));

    @Override
    public Case save(Case c) {
        long nextId = cases.stream()
            .map(Case::id)
            .filter(id -> id != null)
            .mapToLong(Long::longValue)
            .max()
            .orElse(0L) + 1;

        Case saved = new Case(nextId, c.title(), c.status());
        cases.add(saved);
        return saved;
    }

    @Override
    public Case update(Case c) {
        for (int i = 0; i < cases.size(); i++) {
            if (cases.get(i).id().equals(c.id())) {
                cases.set(i, c);
                return c;
            }
        }
        throw new IllegalArgumentException("Case not found: " + c.id());
    }

    @Override
    public void deleteById(Long id) {
        cases.removeIf(c -> c.id().equals(id));
    }

    @Override
    public List<Case> findAll() {
        return cases;
    }

    @Override
    public Optional<Case> findById(Long id) {
        return cases.stream().filter(c -> c.id().equals(id)).findFirst();
    }
}
