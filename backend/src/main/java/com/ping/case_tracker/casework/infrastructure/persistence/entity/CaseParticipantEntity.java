package com.ping.case_tracker.casework.infrastructure.persistence.entity;

import java.io.Serializable;
import java.util.Objects;

import com.ping.case_tracker.casework.domain.model.enums.PartyRole;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "case_participants")
public class CaseParticipantEntity {

    @EmbeddedId
    private CaseParticipantId id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PartyRole role;

    protected CaseParticipantEntity() {
    }

    public CaseParticipantEntity(Long caseId, Long partyId, PartyRole role) {
        this.id = new CaseParticipantId(caseId, partyId);
        this.role = role;
    }

    public Long getCaseId() {
        return id.caseId;
    }

    public Long getPartyId() {
        return id.partyId;
    }

    public PartyRole getRole() {
        return role;
    }

    public CaseParticipantId getId() {
        return id;
    }

    @Embeddable
    public static class CaseParticipantId implements Serializable {

        @Column(name = "case_id")
        private Long caseId;

        @Column(name = "party_id")
        private Long partyId;

        protected CaseParticipantId() {
        }

        public CaseParticipantId(Long caseId, Long partyId) {
            this.caseId = caseId;
            this.partyId = partyId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CaseParticipantId that)) return false;
            return Objects.equals(caseId, that.caseId) && Objects.equals(partyId, that.partyId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(caseId, partyId);
        }
    }
}
