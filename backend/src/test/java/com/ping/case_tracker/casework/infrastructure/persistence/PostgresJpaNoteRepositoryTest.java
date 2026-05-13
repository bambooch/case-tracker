package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import com.ping.case_tracker.support.PostgresContainerConfiguration;
import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.repository.NoteRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.JpaNoteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

@DataJpaTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({JpaNoteRepository.class, PostgresContainerConfiguration.class})
class PostgresJpaNoteRepositoryTest {

    @Autowired
    private NoteRepository repository;

    @Test
    void savesNoteAndFindsItByCaseIdInPostgres() {
        Note saved = repository.save(new Note(null, 1L, "Postgres note", "Bob", Instant.now()));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.findByCaseId(1L))
            .filteredOn(n -> "Postgres note".equals(n.content()))
            .singleElement()
            .satisfies(n -> assertThat(n.author()).isEqualTo("Bob"));
    }
}
