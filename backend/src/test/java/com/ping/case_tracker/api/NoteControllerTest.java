package com.ping.case_tracker.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import com.ping.case_tracker.api.controller.GlobalExceptionHandler;
import com.ping.case_tracker.api.controller.NoteController;
import com.ping.case_tracker.casework.application.NoteService;
import com.ping.case_tracker.casework.support.InMemoryNoteRepository;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NoteController.class)
@Import({
    NoteService.class,
    GlobalExceptionHandler.class,
    InMemoryNoteRepository.class
})
class NoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void addNoteReturnsCreated() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/notes")
                .contentType(APPLICATION_JSON)
                .content("""
                    {
                    "content": "Important note",
                    "author": "John"
                    }
                    """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").isNumber())
            .andExpect(jsonPath("$.caseId").value(1))
            .andExpect(jsonPath("$.content").value("Important note"))
            .andExpect(jsonPath("$.author").value("John"));
    }

    @Test
    void addNoteWithoutContentReturnsBadRequest() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/notes")
                .contentType(APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void updateNoteReturnsUpdatedNote() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/notes")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"content": "Original", "author": "John"}
                    """))
            .andExpect(status().isCreated());

        this.mockMvc.perform(put("/api/cases/1/notes/1")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"content": "Updated content"}
                    """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content").value("Updated content"))
            .andExpect(jsonPath("$.author").value("John"));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void deleteNoteReturnsNoContent() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/notes")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"content": "To be deleted"}
                    """))
            .andExpect(status().isCreated());

        this.mockMvc.perform(delete("/api/cases/1/notes/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void getNoteByUnknownIdReturnsNotFound() throws Exception {
        this.mockMvc.perform(post("/api/cases/1/notes")
                .contentType(APPLICATION_JSON)
                .content("""
                    {"content": "Some note"}
                    """))
            .andExpect(status().isCreated());

        this.mockMvc.perform(get("/api/cases/1/notes/999"))
            .andExpect(status().isNotFound());
    }
}
