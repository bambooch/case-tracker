package com.ping.case_tracker.api.controller;

import java.util.List;

import com.ping.case_tracker.api.dto.cases.CaseDetailResponse;
import com.ping.case_tracker.api.dto.cases.CaseSummaryResponse;
import com.ping.case_tracker.api.dto.cases.CreateCaseRequest;
import com.ping.case_tracker.api.dto.cases.UpdateCaseRequest;
import com.ping.case_tracker.api.dto.note.NoteResponse;
import com.ping.case_tracker.api.dto.participant.ParticipantResponse;
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

    private CaseSummaryResponse toSummaryResponse(Case c) {
        return new CaseSummaryResponse(c.id(), c.title(), c.status().name(),
            caseService.attentionLevelFor(c.status()).name());
    }

    private CaseDetailResponse toDetailResponse(Case c, List<Note> notes, List<CaseParticipant> participants) {
        List<NoteResponse> noteResponses = notes.stream()
            .map(n -> new NoteResponse(n.id(), n.caseId(), n.content(), n.author(), n.createdAt()))
            .toList();
        List<ParticipantResponse> participantResponses = participants.stream()
            .map(p -> {
                Party party = partyService.findById(p.partyId());
                return new ParticipantResponse(p.partyId(), party.name(), p.role().name());
            })
            .toList();
        return new CaseDetailResponse(c.id(), c.title(), c.status().name(),
            caseService.attentionLevelFor(c.status()).name(), noteResponses, participantResponses);
    }
}
