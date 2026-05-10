package com.ping.case_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.ping.case_tracker.casework.CaseCatalogService;
import com.ping.case_tracker.casework.CaseAttentionPolicy;
import com.ping.case_tracker.casework.InMemoryCaseRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseController.class)
@Import({CaseCatalogService.class, CaseAttentionPolicy.class, InMemoryCaseRepository.class})
class CaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getCasesReturnsList() throws Exception {
		this.mockMvc.perform(get("/api/cases"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$").isArray())
			.andExpect(jsonPath("$[0].status").value("OPEN"))
			.andExpect(jsonPath("$[0].attentionLevel").value("IMMEDIATE"));
	}

    @Test
    void getCasesWithLimitReturnsLimitedList() throws Exception {
        this.mockMvc.perform(get("/api/cases?limit=2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }

	@Test
	void getCasesWithStatusReturnsFilteredList() throws Exception {
		this.mockMvc.perform(get("/api/cases?status=IN_REVIEW"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(3))
			.andExpect(jsonPath("$[0].status").value("IN_REVIEW"))
			.andExpect(jsonPath("$[0].attentionLevel").value("FOLLOW_UP"));
	}

	@Test
	void getCasesWithNegativeLimitReturnsEmptyList() throws Exception {
		this.mockMvc.perform(get("/api/cases?limit=-1"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(0));
	}
}