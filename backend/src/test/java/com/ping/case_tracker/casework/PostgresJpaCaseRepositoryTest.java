package com.ping.case_tracker.casework;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.PostgresContainerConfiguration;
import com.ping.case_tracker.casework.persistence.JpaCaseRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaCaseRepository.class, PostgresContainerConfiguration.class})
class PostgresJpaCaseRepositoryTest {

    @Autowired
    private CaseRepository repository;

    @Test
    void savesCaseAndReadsItBackFromPostgreSql() {
        CaseRecord savedCase = repository.save(new CaseRecord(null, "Postgres check", CaseStatus.OPEN));

        assertThat(savedCase.id()).isNotNull();
        assertThat(repository.findAll())
            .filteredOn(caseRecord -> "Postgres check".equals(caseRecord.title()))
            .singleElement()
            .satisfies(caseRecord -> {
                assertThat(caseRecord.title()).isEqualTo("Postgres check");
                assertThat(caseRecord.status()).isEqualTo(CaseStatus.OPEN);
            });
    }
}