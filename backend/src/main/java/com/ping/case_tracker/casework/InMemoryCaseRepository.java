package com.ping.case_tracker.casework;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCaseRepository implements CaseRepository {

    private final List<CaseRecord> cases = new ArrayList<>(List.of(
        new CaseRecord(1L, "Missing documents", CaseStatus.OPEN),
        new CaseRecord(2L, "Payment dispute", CaseStatus.IN_REVIEW),
        new CaseRecord(3L, "Policy update", CaseStatus.CLOSED),
        new CaseRecord(4L, "Claim processing delay", CaseStatus.OPEN),
        new CaseRecord(5L, "Incorrect billing", CaseStatus.IN_REVIEW),
        new CaseRecord(6L, "Coverage inquiry", CaseStatus.CLOSED),
        new CaseRecord(7L, "Fraud investigation", CaseStatus.OPEN),
        new CaseRecord(8L, "Customer feedback", CaseStatus.IN_REVIEW),
        new CaseRecord(9L, "Policy cancellation", CaseStatus.CLOSED)
    ));

    @Override
    public CaseRecord save(CaseRecord caseRecord) {
        long nextId = cases.stream()
            .map(CaseRecord::id)
            .filter(id -> id != null)
            .mapToLong(Long::longValue)
            .max()
            .orElse(0L) + 1;

        CaseRecord savedCase = new CaseRecord(nextId, caseRecord.title(), caseRecord.status());
        cases.add(savedCase);
        return savedCase;
    }

    @Override
    public CaseRecord update(CaseRecord caseRecord) {
        for (int index = 0; index < cases.size(); index++) {
            if (cases.get(index).id().equals(caseRecord.id())) {
                cases.set(index, caseRecord);
                return caseRecord;
            }
        }

        throw new IllegalArgumentException("Case not found: " + caseRecord.id());
    }

    @Override
    public void deleteById(Long id) {
        cases.removeIf(caseRecord -> caseRecord.id().equals(id));
    }

    @Override
    public List<CaseRecord> findAll() {
        return cases;
    }
}