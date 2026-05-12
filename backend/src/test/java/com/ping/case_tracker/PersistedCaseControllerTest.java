package com.ping.case_tracker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.ping.case_tracker.casework.CaseRecord;
import com.ping.case_tracker.casework.CaseRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:persisted-case-controller-test;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
class PersistedCaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseRepository caseRepository;

    @Test
    void createCasePersistsThePostedCase() throws Exception {
        this.mockMvc.perform(post("/api/cases")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "title": "Persisted case",
                      "status": "OPEN"
                    }
                    """))
            .andExpect(status().isCreated());

        assertThat(caseRepository.findAll())
            .extracting(CaseRecord::title)
            .contains("Persisted case");
    }
}