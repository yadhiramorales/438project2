package com.example.demo.controller;

import com.example.demo.dto.NoteCreateRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.dto.NoteUpdateRequest;
import com.example.demo.entity.JobApplication;
import com.example.demo.entity.JobNote;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JobApplicationRepository;
import com.example.demo.repository.JobNoteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NotesController {

    private final JobNoteRepository jobNoteRepository;
    private final JobApplicationRepository jobApplicationRepository;

    public NotesController(
            JobNoteRepository jobNoteRepository,
            JobApplicationRepository jobApplicationRepository
    ) {
        this.jobNoteRepository = jobNoteRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    // GET /notes — fetch all notes sorted by newest first
    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        List<NoteResponse> notes = jobNoteRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(notes);
    }

    // POST /notes — create a new note linked to a job application
    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteCreateRequest req) {
        // jobId in NoteCreateRequest is a String so we parse it to UUID
        UUID jobAppId = UUID.fromString(req.jobId());
        JobApplication jobApplication = jobApplicationRepository.findById(jobAppId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Job application not found"));

        JobNote note = new JobNote(jobApplication, req.noteText());
        JobNote saved = jobNoteRepository.save(note);

        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(saved));
    }

    // PUT /notes/{id} — update note text in Supabase
    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable UUID id,
            @Valid @RequestBody NoteUpdateRequest req
    ) {
        JobNote note = jobNoteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found: " + id));

        note.setNoteText(req.noteText());
        JobNote updated = jobNoteRepository.save(note);

        return ResponseEntity.ok(toResponse(updated));
    }

    // DELETE /notes/{id} — delete note from Supabase
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        if (!jobNoteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found: " + id);
        }
        jobNoteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // converts a JobNote entity to a NoteResponse DTO
    private NoteResponse toResponse(JobNote note) {
        JobApplication job = note.getJobApplication();
        return new NoteResponse(
                note.getId(),
                job.getId().toString(), // UUID → String to match NoteResponse.jobId field
                job.getJobTitle(),
                job.getCompany(),
                note.getNoteText(),
                note.getCreatedAt().toInstant(),
                note.getUpdatedAt().toInstant()
        );
    }
}