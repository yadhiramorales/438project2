package com.example.demo.controller;

import com.example.demo.dto.NoteCreateRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.dto.NoteUpdateRequest;
import com.example.demo.entity.JobApplication;
import com.example.demo.entity.JobNote;
import com.example.demo.repository.JobApplicationRepository;
import com.example.demo.repository.JobNoteRepository;
import jakarta.validation.Valid;
<<<<<<< HEAD
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
=======
import org.springframework.http.MediaType;
>>>>>>> d4b260f (Add HTML views for jobs and notes routes)
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.exception.ResourceNotFoundException;

@RestController
@RequestMapping("/notes")
public class NotesController {

<<<<<<< HEAD
    private final JobNoteRepository jobNoteRepository;
    private final JobApplicationRepository jobApplicationRepository;
    private final Map<UUID, NoteResponse> notes = new LinkedHashMap<>();


    public NotesController(
            JobNoteRepository jobNoteRepository,
            JobApplicationRepository jobApplicationRepository
    ) {
        this.jobNoteRepository = jobNoteRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @GetMapping
=======
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
>>>>>>> d4b260f (Add HTML views for jobs and notes routes)
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> notes = jobNoteRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();

        return ResponseEntity.ok(notes);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteCreateRequest req) {
        JobApplication jobApplication = jobApplicationRepository.findById(req.jobApplicationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Job application not found"));

        JobNote note = new JobNote(jobApplication, req.noteText());
        JobNote saved = jobNoteRepository.save(note);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable UUID id,
            @Valid @RequestBody NoteUpdateRequest req
    ) {
        NoteResponse existing = notes.get(id);

        if (existing == null) {
            throw new ResourceNotFoundException("Note not found: " + id);
        }

        NoteResponse updated = new NoteResponse(
                existing.id(),
                existing.jobApplicationId(),
                existing.jobTitle(),
                existing.company(),
                req.noteText(),
                existing.createdAt(),
                Instant.now()
        );

        notes.put(id, updated);

        return ResponseEntity.ok(updated);
    }

<<<<<<< HEAD

    @DeleteMapping("/{id}")
=======
    @DeleteMapping(value = "/{id}")
>>>>>>> d4b260f (Add HTML views for jobs and notes routes)
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        NoteResponse removed = notes.remove(id);

        if (removed == null) {
            throw new ResourceNotFoundException("Note not found: " + id);
        }

        return ResponseEntity.noContent().build();
    }

    private NoteResponse toResponse(JobNote note) {
        JobApplication job = note.getJobApplication();

        return new NoteResponse(
                note.getId(),
                job.getId(),
                job.getJobTitle(),
                job.getCompany(),
                note.getNoteText(),
                note.getCreatedAt().toInstant(),
                note.getUpdatedAt().toInstant()
        );
    }
}
