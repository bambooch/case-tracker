package com.ping.case_tracker.casework.infrastructure.persistence.repository;

import java.util.List;

import com.ping.case_tracker.casework.infrastructure.persistence.entity.NoteEntity;

import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataNoteJpaRepository extends JpaRepository<NoteEntity, Long> {

    List<NoteEntity> findByCaseId(Long caseId);
}
