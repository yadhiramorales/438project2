package com.example.demo.controller;

import com.example.demo.dto.JobSearchResponse;
import com.example.demo.exception.BadRequestException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        if (keyword == null || keyword.isBlank()) {
            throw new BadRequestException("keyword must not be blank");
        }

        var placeholder = new JobSearchResponse(
                List.of(
                        new JobSearchResponse.JobSummary(
                                "job_123",
                                "Software Engineer",
                                "ExampleCo",
                                location != null && !location.isBlank() ? location : "Remote",
                                "https://example.com/jobs/job_123"
                        )
                ),
                1
        );

        return ResponseEntity.ok(placeholder);
    }
}
