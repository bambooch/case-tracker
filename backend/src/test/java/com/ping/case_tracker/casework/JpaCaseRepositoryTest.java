package com.ping.case_tracker.casework;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.casework.persistence.JpaCaseRepository;

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
        CaseRecord savedCase = repository.save(new CaseRecord(null, "Persistence check", CaseStatus.OPEN));

        assertThat(savedCase.id()).isNotNull();
        assertThat(repository.findAll())
            .filteredOn(caseRecord -> "Persistence check".equals(caseRecord.title()))
            .singleElement()
            .satisfies(caseRecord -> {
                assertThat(caseRecord.title()).isEqualTo("Persistence check");
                assertThat(caseRecord.status()).isEqualTo(CaseStatus.OPEN);
            });
    }
}