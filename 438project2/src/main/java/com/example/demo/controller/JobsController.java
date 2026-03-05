package com.example.demo.controller;

import com.example.demo.dto.JobSearchResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Jobs")
@RestController
@RequestMapping("/jobs")
public class JobsController {

    @Operation(summary = "Search jobs using an external job API (placeholder for now)")
    @GetMapping("/search")
    public ResponseEntity<JobSearchResponse> searchJobs(
            @RequestParam String keyword,
            @RequestParam(required = false) String location
    ) {
        // TODO: integrate external Job API
        var placeholder = new JobSearchResponse(
                List.of(new JobSearchResponse.JobSummary(
                        "job_123",
                        "Software Engineer",
                        "ExampleCo",
                        location != null ? location : "Remote",
                        "https://example.com/jobs/job_123"
                )),
                1
        );
        return ResponseEntity.ok(placeholder);
    }
}