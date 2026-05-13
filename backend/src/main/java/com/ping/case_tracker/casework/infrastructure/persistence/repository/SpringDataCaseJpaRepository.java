package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import com.ping.case_tracker.casework.infrastructure.persistence.entity.CaseEntity;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCaseJpaRepository extends JpaRepository<CaseEntity, Long> {
}
