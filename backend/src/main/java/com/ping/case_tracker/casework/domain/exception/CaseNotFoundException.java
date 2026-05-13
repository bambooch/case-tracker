package com.ping.case_tracker.casework.domain.exception;

public class CaseNotFoundException extends RuntimeException {
    public CaseNotFoundException(Long id) {
        super("Case not found: " + id);
    }
}
