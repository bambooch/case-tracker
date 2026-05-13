package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import java.util.List;

import com.ping.case_tracker.casework.infrastructure.persistence.entity.CaseParticipantEntity;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataParticipantJpaRepository
        extends JpaRepository<CaseParticipantEntity, CaseParticipantEntity.CaseParticipantId> {

    List<CaseParticipantEntity> findByIdCaseId(Long caseId);
}
