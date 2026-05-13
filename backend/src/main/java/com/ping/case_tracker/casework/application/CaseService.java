package com.ping.case_tracker.casework.application;

import java.util.List;

import com.ping.case_tracker.casework.domain.exception.CaseNotFoundException;
import com.ping.case_tracker.casework.domain.model.enums.AttentionLevel;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;
import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.repository.CaseRepository;

import org.springframework.stereotype.Service;

@Service
public class CaseService {

    private final CaseRepository caseRepository;

    public CaseService(CaseRepository caseRepository) {
        this.caseRepository = caseRepository;
    }

    public Case createCase(String title, CaseStatus status) {
        return caseRepository.save(new Case(null, title, status));
    }

    public Case updateCase(Long id, String title, CaseStatus status) {
        return caseRepository.update(new Case(id, title, status));
    }

    public void deleteCase(Long id) {
        caseRepository.deleteById(id);
    }

    public List<Case> findCases(Integer limit, CaseStatus status) {
        long safeLimit = limit == null ? Long.MAX_VALUE : Math.max(limit, 0);

        return caseRepository.findAll().stream()
            .filter(c -> status == null || c.status() == status)
            .limit(safeLimit)
            .toList();
    }

    public Case findById(Long id) {
        return caseRepository.findById(id)
            .orElseThrow(() -> new CaseNotFoundException(id));
    }

    public AttentionLevel attentionLevelFor(CaseStatus status) {
        return switch (status) {
            case OPEN -> AttentionLevel.IMMEDIATE;
            case IN_REVIEW -> AttentionLevel.FOLLOW_UP;
            case CLOSED -> AttentionLevel.ARCHIVE;
        };
    }
}
