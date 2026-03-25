package com.example.demo.controller;

import com.example.demo.dto.JobCreateRequest;
import com.example.demo.entity.JobApplication;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JobApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @GetMapping("/jobs")
    public List<JobApplication> getAllJobs() {
        return jobApplicationRepository.findAll();
    }

    @GetMapping("/jobs/search")
    public List<JobApplication> searchJobs(@RequestParam(required = false, name = "q") String keyword,
                                           @RequestParam(required = false) String location) {
        String kw = normalize(keyword);
        String loc = normalize(location);
        if (kw == null && loc == null) {
            return jobApplicationRepository.findAll();
        }
        return jobApplicationRepository.search(kw, loc);
    }

    @PostMapping("/jobs")
    public ResponseEntity<JobApplication> createJob(@Valid @RequestBody JobCreateRequest request) {
        JobApplication job = new JobApplication();
        job.setJobTitle(request.jobTitle());
        job.setCompany(request.company());
        job.setLocation(request.location());
        JobApplication.Status status = request.status() != null ? request.status() : JobApplication.Status.APPLIED;
        job.setStatus(status);

        JobApplication saved = jobApplicationRepository.save(job);
        URI location = saved.getId() != null ? URI.create("/jobs/" + saved.getId()) : URI.create("/jobs");
        return ResponseEntity.created(location).body(saved);
    }
<<<<<<< HEAD
}
=======

    private String normalize(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
>>>>>>> f14fae8 (Wire landing search and job create to API)
