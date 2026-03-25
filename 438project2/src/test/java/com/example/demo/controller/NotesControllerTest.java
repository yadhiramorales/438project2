package com.example.demo.controller;

import com.example.demo.dto.NoteCreateRequest;
import com.example.demo.dto.NoteUpdateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.config.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Tests the NotesController endpoints without spinning up a full server
@WebMvcTest(NotesController.class)
@ContextConfiguration(classes = {NotesController.class, NotesControllerTest.TestSecurityConfig.class})
@WithMockUser // pretend a logged-in user exists
public class NotesControllerTest {

    // replaces the real SecurityConfig during tests — disables JWT so @WithMockUser works
    @Configuration
    static class TestSecurityConfig {
        @Bean
        SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
            http
                    .csrf(AbstractHttpConfigurer::disable) // disable CSRF for tests
                    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll()); // allow everything
            return http.build();
        }
    }

    // fake browser that lets us send HTTP requests in tests
    @Autowired
    private MockMvc mockMvc;

    // converts Java objects to JSON so we can send them in requests
    @Autowired
    private ObjectMapper objectMapper;


    // GET /notes — should come back with 200 and an empty list
    @Test
    void getAllNotes_shouldReturn200WithEmptyList() throws Exception {
        mockMvc.perform(get("/notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }


    // POST /notes — valid request should return 200 with the note data echoed back
    @Test
    void createNote_withValidRequest_shouldReturn200() throws Exception {
        NoteCreateRequest request = new NoteCreateRequest(
                "job-123",
                "Software Engineer",
                "Google",
                "Great benefits, interesting work"
        );

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.jobId").value("job-123"))
                .andExpect(jsonPath("$.jobTitle").value("Software Engineer"))
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.noteText").value("Great benefits, interesting work"))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }


    // POST /notes — blank jobId should be rejected with 400
    @Test
    void createNote_withMissingJobId_shouldReturn400() throws Exception {
        String badRequest = """
                {
                    "jobId": "",
                    "jobTitle": "Software Engineer",
                    "company": "Google",
                    "noteText": "Some note"
                }
                """;

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }


    // POST /notes — blank noteText should also get rejected, a note with no text is useless
    @Test
    void createNote_withMissingNoteText_shouldReturn400() throws Exception {
        String badRequest = """
                {
                    "jobId": "job-123",
                    "jobTitle": "Software Engineer",
                    "company": "Google",
                    "noteText": ""
                }
                """;

        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }


    // POST /notes — sending an empty {} body should not crash the app, just 400
    @Test
    void createNote_withEmptyBody_shouldReturn400() throws Exception {
        mockMvc.perform(post("/notes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }


    // PUT /notes/{id} — valid update should return 200 with the new text and same ID
    @Test
    void updateNote_withValidRequest_shouldReturn200() throws Exception {
        UUID noteId = UUID.randomUUID();
        NoteUpdateRequest request = new NoteUpdateRequest("Updated note text");

        mockMvc.perform(put("/notes/" + noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(noteId.toString()))
                .andExpect(jsonPath("$.noteText").value("Updated note text"));
    }


    // PUT /notes/{id} — sending blank noteText should get rejected with 400
    @Test
    void updateNote_withBlankNoteText_shouldReturn400() throws Exception {
        UUID noteId = UUID.randomUUID();
        String badRequest = """
                {
                    "noteText": ""
                }
                """;

        mockMvc.perform(put("/notes/" + noteId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }


    // PUT /notes/{id} — "abc" is not a UUID, Spring should auto-reject it with 400
    @Test
    void updateNote_withInvalidUUID_shouldReturn400() throws Exception {
        String badRequest = """
                {
                    "noteText": "Some update"
                }
                """;

        mockMvc.perform(put("/notes/not-a-real-uuid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }


    // DELETE /notes/{id} — successful delete returns 204 (worked but nothing to send back)
    @Test
    void deleteNote_withValidId_shouldReturn204() throws Exception {
        UUID noteId = UUID.randomUUID();

        mockMvc.perform(delete("/notes/" + noteId)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isNoContent());
    }


    // DELETE /notes/{id} — garbage UUID in the URL should return 400 before hitting the DB
    @Test
    void deleteNote_withInvalidUUID_shouldReturn400() throws Exception {
        mockMvc.perform(delete("/notes/not-a-real-uuid")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }
}