package com.ping.case_tracker.casework.application;

import java.time.Instant;
import java.util.List;

import com.ping.case_tracker.casework.domain.exception.NoteNotFoundException;
import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.repository.NoteRepository;

import org.springframework.stereotype.Service;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note addNote(Long caseId, String content, String author) {
        return noteRepository.save(new Note(null, caseId, content, author, Instant.now()));
    }

    public Note updateNote(Long id, String content) {
        Note existing = findById(id);
        return noteRepository.update(new Note(id, existing.caseId(), content, existing.author(), existing.createdAt()));
    }

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public Note findById(Long id) {
        return noteRepository.findById(id)
            .orElseThrow(() -> new NoteNotFoundException(id));
    }

    public List<Note> findByCaseId(Long caseId) {
        return noteRepository.findByCaseId(caseId);
    }
}
