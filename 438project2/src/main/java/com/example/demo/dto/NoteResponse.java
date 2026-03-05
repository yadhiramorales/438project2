package com.example.demo.dto;

import java.time.Instant;
import java.util.UUID;

public record NoteResponse(
        UUID id,
        String jobId,
        String jobTitle,
        String company,
        String noteText,
        Instant createdAt,
        Instant updatedAt
) {
    public static NoteResponse placeholder(NoteCreateRequest req) {
        Instant now = Instant.now();
        return new NoteResponse(
                UUID.randomUUID(),
                req.jobId(),
                req.jobTitle(),
                req.company(),
                req.noteText(),
                now,
                now
        );
    }

    public static NoteResponse updatedPlaceholder(UUID id, NoteUpdateRequest req) {
        Instant now = Instant.now();
        return new NoteResponse(
                id,
                "jobId-TODO",
                "jobTitle-TODO",
                "company-TODO",
                req.noteText(),
                now,
                now
        );
    }
}