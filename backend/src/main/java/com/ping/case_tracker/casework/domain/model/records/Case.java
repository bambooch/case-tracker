package com.ping.case_tracker.casework.domain.model.records;

import com.ping.case_tracker.casework.domain.model.enums.CaseStatus;

public record Case(Long id, String title, CaseStatus status) {
}
