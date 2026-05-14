package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.domain.repository.PartyRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.repository.JpaPartyRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaPartyRepository.class)
class JpaPartyRepositoryTest {

    @Autowired
    private PartyRepository repository;

    @Test
    void savesPartyAndReadsItBack() {
        Party saved = repository.save(new Party(null, "Jane Doe", "jane@example.com"));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.findAll())
            .filteredOn(p -> "Jane Doe".equals(p.name()))
            .singleElement()
            .satisfies(p -> {
                assertThat(p.name()).isEqualTo("Jane Doe");
                assertThat(p.email()).isEqualTo("jane@example.com");
            });
    }

    @Test
    void findByIdReturnsParty() {
        Party saved = repository.save(new Party(null, "John Smith", null));

        assertThat(repository.findById(saved.id()))
            .isPresent()
            .hasValueSatisfying(p -> assertThat(p.name()).isEqualTo("John Smith"));
    }

    @Test
    void findByIdReturnsEmptyForUnknownId() {
        assertThat(repository.findById(999L)).isEmpty();
    }
}
