package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.repository.NoteRepository;
import com.ping.case_tracker.casework.infrastructure.persistence.entity.NoteEntity;

import org.springframework.stereotype.Repository;

@Repository
public class JpaNoteRepository implements NoteRepository {

    private final SpringDataNoteJpaRepository repository;

    public JpaNoteRepository(SpringDataNoteJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Note save(Note note) {
        NoteEntity saved = repository.save(
            new NoteEntity(null, note.caseId(), note.content(), note.author(), note.createdAt()));
        return toDomain(saved);
    }

    @Override
    public Note update(Note note) {
        NoteEntity saved = repository.save(
            new NoteEntity(note.id(), note.caseId(), note.content(), note.author(), note.createdAt()));
        return toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<Note> findById(Long id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Note> findByCaseId(Long caseId) {
        return repository.findByCaseId(caseId).stream().map(this::toDomain).toList();
    }

    private Note toDomain(NoteEntity entity) {
        return new Note(entity.getId(), entity.getCaseId(), entity.getContent(),
            entity.getAuthor(), entity.getCreatedAt());
    }
}
