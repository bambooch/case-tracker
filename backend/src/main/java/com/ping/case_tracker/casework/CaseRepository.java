package com.ping.case_tracker.casework;

import java.util.List;

public interface CaseRepository {

    CaseRecord save(CaseRecord caseRecord);

    CaseRecord update(CaseRecord caseRecord);

    void deleteById(Long id);

    List<CaseRecord> findAll();
}