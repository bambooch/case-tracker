package com.ping.case_tracker.api.controller;

import com.ping.case_tracker.api.dto.participant.AddParticipantRequest;
import com.ping.case_tracker.api.dto.participant.ParticipantResponse;
import com.ping.case_tracker.api.dto.participant.UpdateParticipantRequest;
import com.ping.case_tracker.casework.application.PartyService;
import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.model.records.Party;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Participants")
@RestController
@RequestMapping("/api/cases/{caseId}/participants")
public class ParticipantController {

    private final PartyService partyService;

    public ParticipantController(PartyService partyService) {
        this.partyService = partyService;
    }

    @Operation(summary = "Add a participant to a case")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipantResponse addParticipant(@PathVariable Long caseId,
            @Valid @RequestBody AddParticipantRequest request) {
        CaseParticipant participant = partyService.addParticipant(caseId, request.partyId(), request.role());
        Party party = partyService.findById(participant.partyId());
        return new ParticipantResponse(participant.partyId(), party.name(), participant.role().name());
    }

    @Operation(summary = "Update a participant's role on a case")
    @PutMapping("/{partyId}")
    public ParticipantResponse updateParticipant(@PathVariable Long caseId, @PathVariable Long partyId,
            @Valid @RequestBody UpdateParticipantRequest request) {
        CaseParticipant participant = partyService.updateParticipant(caseId, partyId, request.role());
        Party party = partyService.findById(participant.partyId());
        return new ParticipantResponse(participant.partyId(), party.name(), participant.role().name());
    }

    @Operation(summary = "Remove a participant from a case")
    @DeleteMapping("/{partyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParticipant(@PathVariable Long caseId, @PathVariable Long partyId) {
        partyService.removeParticipant(caseId, partyId);
    }
}
