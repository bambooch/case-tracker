package com.ping.case_tracker.api.controller;

import java.util.Map;

import com.ping.case_tracker.casework.domain.exception.CaseNotFoundException;
import com.ping.case_tracker.casework.domain.exception.PartyNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CaseNotFoundException.class, PartyNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(RuntimeException ex) {
        return Map.of("error", ex.getMessage());
    }
}
