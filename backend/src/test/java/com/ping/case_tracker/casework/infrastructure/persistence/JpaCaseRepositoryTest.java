package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.repository.CaseRepository;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;
import com.ping.case_tracker.casework.infrastructure.persistence.repository.JpaCaseRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaCaseRepository.class)
class JpaCaseRepositoryTest {

    @Autowired
    private CaseRepository repository;

    @Test
    void savesCaseAndReadsItBackFromTheDatabase() {
        Case saved = repository.save(new Case(null, "Persistence check", CaseStatus.OPEN));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.findAll())
            .filteredOn(c -> "Persistence check".equals(c.title()))
            .singleElement()
            .satisfies(c -> {
                assertThat(c.title()).isEqualTo("Persistence check");
                assertThat(c.status()).isEqualTo(CaseStatus.OPEN);
            });
    }

    @Test
    void findByIdReturnsCase() {
        Case saved = repository.save(new Case(null, "Find me", CaseStatus.IN_REVIEW));

        assertThat(repository.findById(saved.id()))
            .isPresent()
            .hasValueSatisfying(c -> assertThat(c.title()).isEqualTo("Find me"));
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        assertThat(repository.findById(999L)).isEmpty();
    }
}
