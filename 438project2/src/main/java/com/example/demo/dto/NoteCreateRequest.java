package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteCreateRequest(
        @NotBlank String jobId,
        @NotBlank String jobTitle,
        @NotBlank String company,
        @NotBlank String noteText
) {}
