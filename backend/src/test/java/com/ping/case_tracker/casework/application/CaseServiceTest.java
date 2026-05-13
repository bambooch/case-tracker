package com.ping.case_tracker.casework.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.application.CaseService;
import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.exception.CaseNotFoundException;
import com.ping.case_tracker.casework.domain.repository.CaseRepository;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;

import org.junit.jupiter.api.Test;

class CaseServiceTest {

    private final CaseRepository caseRepository = new CaseRepository() {
        @Override
        public Case save(Case c) { return c; }

        @Override
        public Case update(Case c) { return c; }

        @Override
        public void deleteById(Long id) {}

        @Override
        public List<Case> findAll() {
            return List.of(
                new Case(1L, "Missing documents", CaseStatus.OPEN),
                new Case(2L, "Payment dispute", CaseStatus.IN_REVIEW),
                new Case(3L, "Policy update", CaseStatus.CLOSED)
            );
        }

        @Override
        public Optional<Case> findById(Long id) {
            return findAll().stream().filter(c -> c.id().equals(id)).findFirst();
        }
    };

    private final CaseService service = new CaseService(caseRepository);

    @Test
    void findCasesReturnsAllCases() {
        List<Case> cases = service.findCases(null, null);

        assertThat(cases).hasSize(3);
        assertThat(cases).extracting(Case::status)
            .containsExactly(CaseStatus.OPEN, CaseStatus.IN_REVIEW, CaseStatus.CLOSED);
    }

    @Test
    void findCasesAppliesStatusFilterBeforeLimit() {
        List<Case> cases = service.findCases(1, CaseStatus.IN_REVIEW);

        assertThat(cases)
            .extracting(Case::title)
            .containsExactly("Payment dispute");
    }

    @Test
    void findCasesTreatsNegativeLimitAsZero() {
        assertThat(service.findCases(-5, null)).isEmpty();
    }

    @Test
    void findByIdReturnsMatchingCase() {
        Case found = service.findById(2L);
        assertThat(found.title()).isEqualTo("Payment dispute");
    }

    @Test
    void findByIdThrowsWhenCaseNotFound() {
        assertThatThrownBy(() -> service.findById(999L))
            .isInstanceOf(CaseNotFoundException.class);
    }
}
