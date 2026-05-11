package com.ping.case_tracker.casework;

import org.springframework.stereotype.Component;

@Component
public class CaseAttentionPolicy {

    public String attentionLevelFor(CaseStatus status) {
        return switch (status) {
            case OPEN -> "IMMEDIATE";
            case IN_REVIEW -> "FOLLOW_UP";
            case CLOSED -> "ARCHIVE";
        };
    }
}