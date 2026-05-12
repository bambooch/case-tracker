package com.ping.case_tracker.casework.persistence;

import com.ping.case_tracker.casework.CaseStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cases")
public class CaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CaseStatus status;

    protected CaseEntity() {
    }

    CaseEntity(Long id, String title, CaseStatus status) {
        this.id = id;
        this.title = title;
        this.status = status;
    }

    Long getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    CaseStatus getStatus() {
        return status;
    }
}