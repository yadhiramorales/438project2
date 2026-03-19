package com.example.demo.dto;

import com.example.demo.entity.JobApplication;
import jakarta.validation.constraints.NotBlank;

public record JobCreateRequest(
        @NotBlank String jobTitle,
        @NotBlank String company,
        String location,
        JobApplication.Status status
) {}
