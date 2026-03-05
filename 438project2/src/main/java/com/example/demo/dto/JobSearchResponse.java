package com.example.demo.dto;

import java.util.List;

public record JobSearchResponse(
        List<JobSummary> jobs,
        int count
) {
    public record JobSummary(
            String jobId,
            String title,
            String company,
            String location,
            String url
    ) {}
}