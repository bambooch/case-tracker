package com.ping.case_tracker.casework.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.repository.NoteRepository;

public class InMemoryNoteRepository implements NoteRepository {

    private final List<Note> notes = new ArrayList<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Note save(Note note) {
        Note saved = new Note(idSequence.getAndIncrement(), note.caseId(), note.content(),
            note.author(), note.createdAt());
        notes.add(saved);
        return saved;
    }

    @Override
    public Note update(Note note) {
        notes.removeIf(n -> n.id().equals(note.id()));
        notes.add(note);
        return note;
    }

    @Override
    public void deleteById(Long id) {
        notes.removeIf(n -> n.id().equals(id));
    }

    @Override
    public Optional<Note> findById(Long id) {
        return notes.stream().filter(n -> n.id().equals(id)).findFirst();
    }

    @Override
    public List<Note> findByCaseId(Long caseId) {
        return notes.stream().filter(n -> n.caseId().equals(caseId)).toList();
    }
}
