package com.ping.case_tracker.api.controller;

import com.ping.case_tracker.api.dto.note.AddNoteRequest;
import com.ping.case_tracker.api.dto.note.NoteResponse;
import com.ping.case_tracker.api.dto.note.UpdateNoteRequest;
import com.ping.case_tracker.casework.application.NoteService;
import com.ping.case_tracker.casework.domain.model.records.Note;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Notes")
@RestController
@RequestMapping("/api/cases/{caseId}/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Operation(summary = "Add a note to a case")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse addNote(@PathVariable Long caseId, @Valid @RequestBody AddNoteRequest request) {
        return toResponse(noteService.addNote(caseId, request.content(), request.author()));
    }

    @Operation(summary = "Get a note by ID")
    @GetMapping("/{noteId}")
    public NoteResponse getNote(@PathVariable Long caseId, @PathVariable Long noteId) {
        return toResponse(noteService.findById(noteId));
    }

    @Operation(summary = "Update a note")
    @PutMapping("/{noteId}")
    public NoteResponse updateNote(@PathVariable Long caseId, @PathVariable Long noteId,
            @Valid @RequestBody UpdateNoteRequest request) {
        return toResponse(noteService.updateNote(noteId, request.content()));
    }

    @Operation(summary = "Delete a note")
    @DeleteMapping("/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable Long caseId, @PathVariable Long noteId) {
        noteService.deleteNote(noteId);
    }

    private NoteResponse toResponse(Note note) {
        return new NoteResponse(note.id(), note.caseId(), note.content(), note.author(), note.createdAt());
    }
}
