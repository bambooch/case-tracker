package com.ping.case_tracker.casework.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.repository.NoteRepository;

import org.junit.jupiter.api.Test;

class NoteServiceTest {

    private final NoteRepository noteRepository = mock(NoteRepository.class);
    private final NoteService service = new NoteService(noteRepository);

    @Test
    void addNoteCallsSaveWithCorrectCaseId() {
        Note expected = new Note(1L, 5L, "Test note", "Alice", Instant.now());
        when(noteRepository.save(any())).thenReturn(expected);

        Note result = service.addNote(5L, "Test note", "Alice");

        assertThat(result.caseId()).isEqualTo(5L);
        assertThat(result.content()).isEqualTo("Test note");
        verify(noteRepository).save(any());
    }

    @Test
    void deleteNoteDelegatesToRepository() {
        service.deleteNote(42L);
        verify(noteRepository).deleteById(42L);
    }

    @Test
    void findByCaseIdDelegatesToRepository() {
        Note note = new Note(1L, 3L, "content", "Bob", Instant.now());
        when(noteRepository.findByCaseId(3L)).thenReturn(List.of(note));

        List<Note> result = service.findByCaseId(3L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).caseId()).isEqualTo(3L);
    }
}
