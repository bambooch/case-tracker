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
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=create-drop")
@AutoConfigureMockMvc
@Import(PostgresContainerConfiguration.class)
class PostgresPersistedCaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CaseRepository caseRepository;

    @Test
    void createCasePersistsThePostedCaseInPostgreSql() throws Exception {
        this.mockMvc.perform(post("/api/cases")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                      "title": "Persisted postgres case",
                      "status": "OPEN"
                    }
                    """))
            .andExpect(status().isCreated());

        assertThat(caseRepository.findAll())
            .extracting(CaseRecord::title)
            .contains("Persisted postgres case");
    }
}