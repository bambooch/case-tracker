package com.ping.case_tracker.casework.domain.exception;

public class PartyNotFoundException extends RuntimeException {
    public PartyNotFoundException(Long id) {
        super("Party not found: " + id);
    }
}
