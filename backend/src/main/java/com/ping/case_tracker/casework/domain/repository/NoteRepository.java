package com.ping.case_tracker.casework.domain.repository;

import java.util.List;
import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.Note;

public interface NoteRepository {

    Note save(Note note);

    Note update(Note note);

    void deleteById(Long id);

    Optional<Note> findById(Long id);

    List<Note> findByCaseId(Long caseId);
}
