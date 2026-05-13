package com.ping.case_tracker.casework.application;

import java.time.Instant;
import java.util.List;

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

    public void deleteNote(Long id) {
        noteRepository.deleteById(id);
    }

    public List<Note> findByCaseId(Long caseId) {
        return noteRepository.findByCaseId(caseId);
    }
}
