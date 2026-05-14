package com.ping.case_tracker.casework.infrastructure.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.ping.case_tracker.casework.domain.model.records.CaseParticipant;
import com.ping.case_tracker.casework.domain.repository.CaseParticipantRepository;
import com.ping.case_tracker.casework.domain.model.enums.PartyRole;
import com.ping.case_tracker.casework.infrastructure.persistence.repository.JpaCaseParticipantRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import(JpaCaseParticipantRepository.class)
class JpaCaseParticipantRepositoryTest {

    @Autowired
    private CaseParticipantRepository repository;

    @Test
    void savesParticipantAndFindsItByCaseId() {
        repository.save(new CaseParticipant(1L, 10L, PartyRole.CLAIMANT));

        assertThat(repository.findByCaseId(1L))
            .singleElement()
            .satisfies(p -> {
                assertThat(p.partyId()).isEqualTo(10L);
                assertThat(p.role()).isEqualTo(PartyRole.CLAIMANT);
            });
    }

    @Test
    void removesParticipantByCompositeKey() {
        repository.save(new CaseParticipant(2L, 20L, PartyRole.RESPONDENT));

        repository.deleteByCaseIdAndPartyId(2L, 20L);

        assertThat(repository.findByCaseId(2L)).isEmpty();
    }

    @Test
    void findByCaseIdReturnsOnlyParticipantsForThatCase() {
        repository.save(new CaseParticipant(3L, 30L, PartyRole.COUNSEL));
        repository.save(new CaseParticipant(4L, 40L, PartyRole.WITNESS));

        assertThat(repository.findByCaseId(3L)).hasSize(1);
        assertThat(repository.findByCaseId(4L)).hasSize(1);
    }
}
