package com.ping.case_tracker.api.dto.cases;

import java.util.List;

import com.ping.case_tracker.api.dto.note.NoteResponse;
import com.ping.case_tracker.api.dto.participant.ParticipantResponse;

public record CaseDetailResponse(
    Long id,
    String title,
    String status,
    String attentionLevel,
    List<NoteResponse> notes,
    List<ParticipantResponse> participants
) {
}
