package com.ping.case_tracker.casework;

import java.util.List;

public interface CaseRepository {

    List<CaseRecord> findAll();
}