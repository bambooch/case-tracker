package com.ping.case_tracker.api.controller;

import java.util.List;

import com.ping.case_tracker.api.dto.note.AddNoteRequest;
import com.ping.case_tracker.api.dto.note.UpdateNoteRequest;
import com.ping.case_tracker.api.dto.participant.AddParticipantRequest;
import com.ping.case_tracker.api.dto.participant.UpdateParticipantRequest;
import com.ping.case_tracker.api.dto.cases.CaseDetailResponse;
import com.ping.case_tracker.api.dto.cases.CaseSummaryResponse;
import com.ping.case_tracker.api.dto.cases.CreateCaseRequest;
import com.ping.case_tracker.api.dto.note.NoteResponse;
import com.ping.case_tracker.api.dto.participant.ParticipantResponse;
import com.ping.case_tracker.api.dto.cases.UpdateCaseRequest;
import com.ping.case_tracker.casework.application.CaseService;
import com.ping.case_tracker.casework.application.NoteService;
import com.ping.case_tracker.casework.application.PartyService;
import com.ping.case_tracker.casework.domain.model.records.Case;
import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;
import com.ping.case_tracker.casework.domain.model.records.Note;
import com.ping.case_tracker.casework.domain.model.records.Party;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Cases")
@RestController
@RequestMapping("/api/cases")
public class CaseController {

    private final CaseService caseService;
    private final NoteService noteService;
    private final PartyService partyService;

    public CaseController(CaseService caseService, NoteService noteService, PartyService partyService) {
        this.caseService = caseService;
        this.noteService = noteService;
        this.partyService = partyService;
    }

    @Operation(summary = "List cases")
    @GetMapping
    public List<CaseSummaryResponse> getCases(
            @RequestParam(required = false) Integer limit,
            @RequestParam(required = false) CaseStatus status) {
        return caseService.findCases(limit, status).stream()
            .map(this::toSummaryResponse)
            .toList();
    }

    @Operation(summary = "Get case detail with notes and participants")
    @GetMapping("/{id}")
    public CaseDetailResponse getCase(@PathVariable Long id) {
        Case c = caseService.findById(id);
        List<Note> notes = noteService.findByCaseId(id);
        List<CaseParticipant> participants = partyService.findParticipants(id);
        return toDetailResponse(c, notes, participants);
    }

    @Operation(summary = "Create a case")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CaseSummaryResponse createCase(@Valid @RequestBody CreateCaseRequest request) {
        return toSummaryResponse(caseService.createCase(request.title(), request.status()));
    }

    @Operation(summary = "Update a case")
    @PutMapping("/{id}")
    public CaseSummaryResponse updateCase(@PathVariable Long id, @Valid @RequestBody UpdateCaseRequest request) {
        return toSummaryResponse(caseService.updateCase(id, request.title(), request.status()));
    }

    @Operation(summary = "Delete a case")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCase(@PathVariable Long id) {
        caseService.deleteCase(id);
    }

    @Operation(summary = "Add a note to a case")
    @PostMapping("/{id}/notes")
    @ResponseStatus(HttpStatus.CREATED)
    public NoteResponse addNote(@PathVariable Long id, @Valid @RequestBody AddNoteRequest request) {
        return toNoteResponse(noteService.addNote(id, request.content(), request.author()));
    }

    @Operation(summary = "Get a note by ID")
    @GetMapping("/{id}/notes/{noteId}")
    public NoteResponse getNote(@PathVariable Long id, @PathVariable Long noteId) {
        return toNoteResponse(noteService.findById(noteId));
    }

    @Operation(summary = "Update a note")
    @PutMapping("/{id}/notes/{noteId}")
    public NoteResponse updateNote(@PathVariable Long id, @PathVariable Long noteId,
            @Valid @RequestBody UpdateNoteRequest request) {
        return toNoteResponse(noteService.updateNote(noteId, request.content()));
    }

    @Operation(summary = "Remove a note from a case")
    @DeleteMapping("/{id}/notes/{noteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteNote(@PathVariable Long id, @PathVariable Long noteId) {
        noteService.deleteNote(noteId);
    }

    @Operation(summary = "Add a party as a participant on a case")
    @PostMapping("/{id}/participants")
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantResponse addParticipant(@PathVariable Long id, @Valid @RequestBody AddParticipantRequest request) {
        CaseParticipant participant = partyService.addParticipant(id, request.partyId(), request.role());
        Party party = partyService.findById(participant.partyId());
        return new ParticipantResponse(participant.partyId(), party.name(), participant.role().name());
    }

    @Operation(summary = "Update a participant's role on a case")
    @PutMapping("/{id}/participants/{partyId}")
    public ParticipantResponse updateParticipant(@PathVariable Long id, @PathVariable Long partyId,
            @Valid @RequestBody UpdateParticipantRequest request) {
        CaseParticipant participant = partyService.updateParticipant(id, partyId, request.role());
        Party party = partyService.findById(participant.partyId());
        return new ParticipantResponse(participant.partyId(), party.name(), participant.role().name());
    }

    @Operation(summary = "Remove a participant from a case")
    @DeleteMapping("/{id}/participants/{partyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParticipant(@PathVariable Long id, @PathVariable Long partyId) {
        partyService.removeParticipant(id, partyId);
    }

    private CaseSummaryResponse toSummaryResponse(Case c) {
        return new CaseSummaryResponse(c.id(), c.title(), c.status().name(),
            caseService.attentionLevelFor(c.status()).name());
    }

    private CaseDetailResponse toDetailResponse(Case c, List<Note> notes, List<CaseParticipant> participants) {
        List<NoteResponse> noteResponses = notes.stream().map(this::toNoteResponse).toList();
        List<ParticipantResponse> participantResponses = participants.stream()
            .map(p -> {
                Party party = partyService.findById(p.partyId());
                return new ParticipantResponse(p.partyId(), party.name(), p.role().name());
            })
            .toList();
        return new CaseDetailResponse(c.id(), c.title(), c.status().name(),
            caseService.attentionLevelFor(c.status()).name(), noteResponses, participantResponses);
    }

    private NoteResponse toNoteResponse(Note note) {
        return new NoteResponse(note.id(), note.caseId(), note.content(), note.author(), note.createdAt());
    }
}
