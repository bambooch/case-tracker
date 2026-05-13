package com.ping.case_tracker.casework.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.repository.CaseParticipantRepository;
import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.domain.exception.PartyNotFoundException;
import com.ping.case_tracker.casework.domain.repository.PartyRepository;
import com.ping.case_tracker.casework.domain.model.enums.PartyRole;

import org.junit.jupiter.api.Test;

class PartyServiceTest {

    private final PartyRepository partyRepository = mock(PartyRepository.class);
    private final CaseParticipantRepository participantRepository = mock(CaseParticipantRepository.class);
    private final PartyService service = new PartyService(partyRepository, participantRepository);

    @Test
    void createPartyPersistsAndReturns() {
        Party expected = new Party(1L, "Jane Doe", "jane@example.com");
        when(partyRepository.save(any())).thenReturn(expected);

        Party result = service.createParty("Jane Doe", "jane@example.com");

        assertThat(result.name()).isEqualTo("Jane Doe");
        verify(partyRepository).save(any());
    }

    @Test
    void findByIdReturnsParty() {
        Party expected = new Party(1L, "Jane Doe", "jane@example.com");
        when(partyRepository.findById(1L)).thenReturn(Optional.of(expected));

        assertThat(service.findById(1L).name()).isEqualTo("Jane Doe");
    }

    @Test
    void findByIdThrowsForMissingParty() {
        when(partyRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
            .isInstanceOf(PartyNotFoundException.class);
    }

    @Test
    void addParticipantSavesParticipant() {
        CaseParticipant expected = new CaseParticipant(1L, 2L, PartyRole.CLAIMANT);
        when(participantRepository.save(any())).thenReturn(expected);

        CaseParticipant result = service.addParticipant(1L, 2L, PartyRole.CLAIMANT);

        assertThat(result.role()).isEqualTo(PartyRole.CLAIMANT);
        verify(participantRepository).save(any());
    }

    @Test
    void removeParticipantDelegatesToRepository() {
        service.removeParticipant(1L, 2L);
        verify(participantRepository).deleteByCaseIdAndPartyId(1L, 2L);
    }
}
