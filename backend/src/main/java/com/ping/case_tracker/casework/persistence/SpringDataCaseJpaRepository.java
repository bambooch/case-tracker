package com.ping.case_tracker.casework.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataCaseJpaRepository extends JpaRepository<CaseEntity, Long> {
}