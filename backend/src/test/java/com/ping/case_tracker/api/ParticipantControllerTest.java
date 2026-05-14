package com.ping.case_tracker.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.ping.case_tracker.api.controller.GlobalExceptionHandler;
import com.ping.case_tracker.api.controller.ParticipantController;
import com.ping.case_tracker.casework.application.PartyService;
import com.ping.case_tracker.casework.domain.model.records.Party;
import com.ping.case_tracker.casework.support.InMemoryCaseParticipantRepository;
import com.ping.case_tracker.casework.support.InMemoryPartyRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ParticipantController.class)
@Import({
    PartyService.class,
    GlobalExceptionHandler.class,
    InMemoryPartyRepository.class,
    InMemoryCaseParticipantRepository.class
})
class ParticipantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private InMemoryPartyRepository partyRepository;

    @BeforeEach
    void setUp() {
        partyRepository.save(new Party(null, "Jane Doe", "jane@example.com"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addParticipantReturnsCreated() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/participants")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"partyId": 1, "role": "CLAIMANT"}
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.partyId").value(1))
            .andExpect(jsonPath("$.role").value("CLAIMANT"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateParticipantRoleReturnsUpdated() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/participants")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"partyId": 1, "role": "CLAIMANT"}
                    """));

        this.mockMvc.perform(put("/api/cases/1/participants/1")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"role": "RESPONDENT"}
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.role").value("RESPONDENT"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void removeParticipantReturnsNoContent() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/participants")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"partyId": 1, "role": "CLAIMANT"}
                    """));

        this.mockMvc.perform(delete("/api/cases/1/participants/1"))
            .andExpect(status().isNoContent());
    }
}
