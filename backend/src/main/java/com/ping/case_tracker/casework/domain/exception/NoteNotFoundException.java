package com.ping.case_tracker.casework.domain.exception;

public class NoteNotFoundException extends RuntimeException {
    public NoteNotFoundException(Long id) {
        super("Note not found: " + id);
    }
}
