package com.ping.case_tracker.casework;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CaseCatalogService {

    private final CaseRepository caseRepository;
    private final CaseAttentionPolicy caseAttentionPolicy;

    public CaseCatalogService(CaseRepository caseRepository, CaseAttentionPolicy caseAttentionPolicy) {
        this.caseRepository = caseRepository;
        this.caseAttentionPolicy = caseAttentionPolicy;
    }

    public CaseSummary createCase(CaseRecord caseRecord) {
        CaseRecord savedCase = caseRepository.save(new CaseRecord(null, caseRecord.title(), caseRecord.status()));
        return toSummary(savedCase);
    }

    public CaseSummary updateCase(Long id, CaseRecord caseRecord) {
        CaseRecord updatedCase = caseRepository.update(new CaseRecord(id, caseRecord.title(), caseRecord.status()));
        return toSummary(updatedCase);
    }

    public void deleteCase(Long id) {
        caseRepository.deleteById(id);
    }

    public List<CaseSummary> findCases(Integer limit, CaseStatus status) {
        long safeLimit = limit == null ? Long.MAX_VALUE : Math.max(limit, 0);

        return caseRepository.findAll().stream()
            .filter(caseRecord -> status == null || caseRecord.status() == status)
            .limit(safeLimit)
            .map(this::toSummary)
            .toList();
    }

    private CaseSummary toSummary(CaseRecord caseRecord) {
        return new CaseSummary(
            caseRecord.id(),
            caseRecord.title(),
            caseRecord.status().name(),
            caseAttentionPolicy.attentionLevelFor(caseRecord.status())
        );
    }
}