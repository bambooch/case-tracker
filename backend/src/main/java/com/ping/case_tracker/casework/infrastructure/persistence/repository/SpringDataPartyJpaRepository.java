package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import com.ping.case_tracker.casework.infrastructure.persistence.entity.PartyEntity;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataPartyJpaRepository extends JpaRepository<PartyEntity, Long> {
}
