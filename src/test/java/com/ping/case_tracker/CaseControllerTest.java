package com.ping.case_tracker;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseController.class)
class CaseControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void getCasesReturnsList() throws Exception {
		this.mockMvc.perform(get("/api/cases"))
			.andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray());
	}

    @Test
    void getCasesWithLimitReturnsLimitedList() throws Exception {
        this.mockMvc.perform(get("/api/cases?limit=2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2));
    }
}