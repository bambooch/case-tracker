package com.ping.case_tracker.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.ping.case_tracker.api.controller.GlobalExceptionHandler;
import com.ping.case_tracker.api.controller.PartyController;
import com.ping.case_tracker.casework.application.PartyService;
import com.ping.case_tracker.casework.support.InMemoryPartyRepository;
import com.ping.case_tracker.casework.support.InMemoryCaseParticipantRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PartyController.class)
@Import({
    PartyService.class,
    GlobalExceptionHandler.class,
    InMemoryPartyRepository.class,
    InMemoryCaseParticipantRepository.class
})
class PartyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createPartyReturnsCreated() throws Exception {
        this.mockMvc.perform(post("/api/parties")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                    "name": "Jane Doe",
                    "email": "jane@example.com"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.name").value("Jane Doe"))
            .andExpect(jsonPath("$.email").value("jane@example.com"));
    }

    @Test
    void createPartyWithoutNameReturnsBadRequest() throws Exception {
        this.mockMvc.perform(post("/api/parties")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                    "email": "jane@example.com"
                    }
                    """))
            .andExpect(status().isBadRequest());
    }

    @Test
    void getAllPartiesReturnsList() throws Exception {
        this.mockMvc.perform(get("/api/parties"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getPartyByUnknownIdReturnsNotFound() throws Exception {
        this.mockMvc.perform(get("/api/parties/999"))
            .andExpect(status().isNotFound());
    }
}
