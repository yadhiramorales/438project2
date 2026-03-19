
/**
 * JobController
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 3/4/26 7:15 PM
 * @since 3/4/26
 */

package com.example.demo.controller;

import com.example.demo.entity.JobApplication;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JobApplicationRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobController {

    private final JobApplicationRepository jobApplicationRepository;

    public JobController(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @GetMapping("/jobs")
    public List<JobApplication> getAllJobs() {
        return jobApplicationRepository.findAll();
    }

    @GetMapping("/jobs/{id}")
    public JobApplication getJobById(@PathVariable UUID id) {
        return jobApplicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Job application not found: " + id
                ));
    }
}
