package com.ping.case_tracker.api.controller;

import java.util.List;

import com.ping.case_tracker.api.dto.party.CreatePartyRequest;
import com.ping.case_tracker.api.dto.party.PartyResponse;
import com.ping.case_tracker.casework.application.PartyService;
import com.ping.case_tracker.casework.domain.model.records.Party;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Parties")
@RestController
@RequestMapping("/api/parties")
public class PartyController {

    private final PartyService partyService;

    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    @Operation(summary = "List all parties")
    @GetMapping
    public List<PartyResponse> getParties() {
        return partyService.findAll().stream().map(this::toResponse).toList();
    }

    @Operation(summary = "Create a party")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PartyResponse createParty(@Valid @RequestBody CreatePartyRequest request) {
        return toResponse(partyService.createParty(request.name(), request.email()));
    }

    @Operation(summary = "Get a party by ID")
    @GetMapping("/{id}")
    public PartyResponse getParty(@PathVariable Long id) {
        return toResponse(partyService.findById(id));
    }

    private PartyResponse toResponse(Party party) {
        return new PartyResponse(party.id(), party.name(), party.email());
    }
}
