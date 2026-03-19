package com.example.demo.controller;

import com.example.demo.dto.JobCreateRequest;
import com.example.demo.entity.JobApplication;
import com.example.demo.repository.JobApplicationRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}