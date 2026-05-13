package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.repository.NoteRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.JpaNoteRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaNoteRepository.class)
class JpaNoteRepositoryTest {

    @Autowired
    private NoteRepository repository;

    @Test
    void savesNoteAndFindsItByCaseId() {
        Note saved = repository.save(new Note(null, 1L, "Test content", "Alice", Instant.now()));

        assertThat(saved.id()).isNotNull();
        assertThat(repository.findByCaseId(1L))
            .singleElement()
            .satisfies(n -> {
                assertThat(n.content()).isEqualTo("Test content");
                assertThat(n.author()).isEqualTo("Alice");
                assertThat(n.caseId()).isEqualTo(1L);
            });
    }

    @Test
    void deleteByIdRemovesNote() {
        Note saved = repository.save(new Note(null, 2L, "To delete", null, Instant.now()));

        repository.deleteById(saved.id());

        assertThat(repository.findByCaseId(2L)).isEmpty();
    }

    @Test
    void findByCaseIdReturnsOnlyNotesForThatCase() {
        repository.save(new Note(null, 10L, "Case 10 note", null, Instant.now()));
        repository.save(new Note(null, 20L, "Case 20 note", null, Instant.now()));

        assertThat(repository.findByCaseId(10L)).hasSize(1);
        assertThat(repository.findByCaseId(20L)).hasSize(1);
    }
}
