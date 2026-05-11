package com.ping.case_tracker.casework;

import java.util.List;

public interface CaseRepository {

    CaseRecord save(CaseRecord caseRecord);

    List<CaseRecord> findAll();
}