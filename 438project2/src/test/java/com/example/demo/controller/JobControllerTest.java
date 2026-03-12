package com.example.demo.controller;

import com.example.demo.entity.JobApplication;
import com.example.demo.repository.JobApplicationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Tests the JobController GET /jobs endpoint without a real database
@WebMvcTest(JobController.class)
@WithMockUser // pretend a user is logged in so Spring Security doesn't block with 401
public class JobControllerTest {

    // fake browser for sending requests
    @Autowired
    private MockMvc mockMvc;

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
}
