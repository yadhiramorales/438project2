package com.example.demo.controller;

import com.example.demo.dto.NoteCreateRequest;
import com.example.demo.dto.NoteResponse;
import com.example.demo.dto.NoteUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/notes")
public class NotesController {

    @GetMapping
    public ResponseEntity<List<NoteResponse>> getAllNotes() {
        // TODO: connect to service/repository
        return ResponseEntity.ok(List.of());
    }

    @PostMapping
    public ResponseEntity<NoteResponse> createNote(@Valid @RequestBody NoteCreateRequest req) {
        // TODO: persist and return created note
        NoteResponse placeholder = NoteResponse.placeholder(req);
        return ResponseEntity.ok(placeholder);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteResponse> updateNote(
            @PathVariable UUID id,
            @Valid @RequestBody NoteUpdateRequest req
    ) {
        // TODO: update existing note
        return ResponseEntity.ok(NoteResponse.updatedPlaceholder(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable UUID id) {
        // TODO: delete existing note
        return ResponseEntity.noContent().build();
    }
}