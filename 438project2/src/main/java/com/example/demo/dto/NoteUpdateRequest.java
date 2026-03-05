package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public record NoteUpdateRequest(
        @NotBlank String noteText
) {}
