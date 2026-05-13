package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.support.PostgresContainerConfiguration;
import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.repository.CaseRepository;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;
import com.ping.case_tracker.casework.infrastructure.persistence.repository.JpaCaseRepository;

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
        Case saved = repository.save(new Case(null, "Postgres check", CaseStatus.OPEN));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.findAll())
            .filteredOn(c -> "Postgres check".equals(c.title()))
            .singleElement()
            .satisfies(c -> {
                assertThat(c.title()).isEqualTo("Postgres check");
                assertThat(c.status()).isEqualTo(CaseStatus.OPEN);
            });
    }
}
