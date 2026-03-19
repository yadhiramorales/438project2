package com.example.demo.controller;

import com.example.demo.dto.JobCreateRequest;
import com.example.demo.entity.JobApplication;
import com.example.demo.repository.JobApplicationRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.config.TestSecurityConfig;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Tests the JobController GET /jobs endpoint without a real database
@WebMvcTest(JobController.class)
@Import(TestSecurityConfig.class)
@WithMockUser // pretend a user is logged in so Spring Security doesn't block with 401
public class JobControllerTest {

    // fake browser for sending requests
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // MockBean replaces the real repository with a fake one we can control
    @MockBean
    private JobApplicationRepository jobApplicationRepository;


    // GET /jobs — when the DB has jobs, should return 200 with a list
    @Test
    void getAllJobs_shouldReturn200WithJobList() throws Exception {
        // set up two fake jobs to return from the mock repository
        JobApplication job1 = new JobApplication("Software Engineer", "Google", "Remote", JobApplication.Status.APPLIED);
        JobApplication job2 = new JobApplication("Backend Developer", "Meta", "New York", JobApplication.Status.INTERVIEW);

        // tell the fake repository to return these two jobs when findAll() is called
        when(jobApplicationRepository.findAll()).thenReturn(List.of(job1, job2));

        mockMvc.perform(get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())                                         // expect HTTP 200
                .andExpect(jsonPath("$").isArray())                                 // response is a list
                .andExpect(jsonPath("$.length()").value(2))                         // list has 2 items
                .andExpect(jsonPath("$[0].jobTitle").value("Software Engineer"))    // first job matches
                .andExpect(jsonPath("$[0].company").value("Google"))
                .andExpect(jsonPath("$[1].jobTitle").value("Backend Developer"))    // second job matches
                .andExpect(jsonPath("$[1].company").value("Meta"));
    }


    // GET /jobs — when the DB is empty, should still return 200 with an empty list
    @Test
    void getAllJobs_whenNoJobs_shouldReturn200WithEmptyList() throws Exception {
        // tell the fake repository to return nothing
        when(jobApplicationRepository.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())                     // expect HTTP 200
                .andExpect(jsonPath("$").isArray())             // response is still a list
                .andExpect(jsonPath("$.length()").value(0));    // just empty
    }


    // GET /jobs — checks that status field comes back correctly as a string
    @Test
    void getAllJobs_shouldReturnCorrectStatus() throws Exception {
        JobApplication job = new JobApplication("Data Analyst", "Amazon", "Seattle", JobApplication.Status.OFFER);

        when(jobApplicationRepository.findAll()).thenReturn(List.of(job));

        mockMvc.perform(get("/jobs")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status").value("OFFER")); // enum should serialize as string
    }

    // POST /jobs — valid request should persist and return 201 with the created job
    @Test
    void createJob_withValidRequest_shouldReturn201() throws Exception {
        JobCreateRequest request = new JobCreateRequest(
                "Software Engineer",
                "Google",
                "Remote",
                JobApplication.Status.APPLIED
        );

        UUID jobId = UUID.randomUUID();
        JobApplication savedJob = new JobApplication("Software Engineer", "Google", "Remote", JobApplication.Status.APPLIED);
        ReflectionTestUtils.setField(savedJob, "id", jobId);

        when(jobApplicationRepository.save(any(JobApplication.class))).thenReturn(savedJob);

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/jobs/" + jobId))
                .andExpect(jsonPath("$.id").value(jobId.toString()))
                .andExpect(jsonPath("$.jobTitle").value("Software Engineer"))
                .andExpect(jsonPath("$.company").value("Google"))
                .andExpect(jsonPath("$.status").value("APPLIED"));
    }

    // POST /jobs — missing jobTitle should return 400
    @Test
    void createJob_withBlankTitle_shouldReturn400() throws Exception {
        String badRequest = """
                {
                  "jobTitle": "",
                  "company": "Google",
                  "location": "Remote"
                }
                """;

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }

    // POST /jobs — missing company should return 400
    @Test
    void createJob_withBlankCompany_shouldReturn400() throws Exception {
        String badRequest = """
                {
                  "jobTitle": "Software Engineer",
                  "company": "",
                  "location": "Remote"
                }
                """;

        mockMvc.perform(post("/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(badRequest)
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isBadRequest());
    }
}
