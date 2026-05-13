package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.support.PostgresContainerConfiguration;
import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.domain.repository.PartyRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.JpaPartyRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaPartyRepository.class, PostgresContainerConfiguration.class})
class PostgresJpaPartyRepositoryTest {

    @Autowired
    private PartyRepository repository;

    @Test
    void savesPartyAndReadsItBackFromPostgres() {
        Party saved = repository.save(new Party(null, "Postgres Party", "pg@example.com"));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.findAll())
            .filteredOn(p -> "Postgres Party".equals(p.name()))
            .singleElement()
            .satisfies(p -> assertThat(p.email()).isEqualTo("pg@example.com"));
    }
}
