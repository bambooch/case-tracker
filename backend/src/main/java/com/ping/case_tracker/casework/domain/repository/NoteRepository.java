package com.ping.case_tracker.casework.domain.repository;

import java.util.List;

import com.ping.case_tracker.casework.domain.model.records.Note;

public interface NoteRepository {

    Note save(Note note);

    void deleteById(Long id);

    List<Note> findByCaseId(Long caseId);
}
