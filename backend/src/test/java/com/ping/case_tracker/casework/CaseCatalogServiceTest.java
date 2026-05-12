package com.ping.case_tracker.casework;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class CaseCatalogServiceTest {

    private final CaseRepository caseRepository = new CaseRepository() {
        @Override
        public CaseRecord save(CaseRecord caseRecord) {
            return caseRecord;
        }

        @Override
        public CaseRecord update(CaseRecord caseRecord) {
            return caseRecord;
        }

        @Override
        public void deleteById(Long id) {
        }

        @Override
        public List<CaseRecord> findAll() {
            return List.of(
                new CaseRecord(1L, "Missing documents", CaseStatus.OPEN),
                new CaseRecord(2L, "Payment dispute", CaseStatus.IN_REVIEW),
                new CaseRecord(3L, "Policy update", CaseStatus.CLOSED)
            );
        }
    };

    private final CaseCatalogService service = new CaseCatalogService(caseRepository, new CaseAttentionPolicy());

    @Test
    void findCasesBuildsAttentionLevelsFromPolicy() {
        List<CaseSummary> cases = service.findCases(null, null);

        assertThat(cases)
            .extracting(CaseSummary::attentionLevel)
            .containsExactly("IMMEDIATE", "FOLLOW_UP", "ARCHIVE");
    }

    @Test
    void findCasesAppliesStatusFilterBeforeLimit() {
        List<CaseSummary> cases = service.findCases(1, CaseStatus.IN_REVIEW);

        assertThat(cases)
            .extracting(CaseSummary::title)
            .containsExactly("Payment dispute");
    }

    @Test
    void findCasesTreatsNegativeLimitAsZero() {
        assertThat(service.findCases(-5, null)).isEmpty();
    }
}