package com.example.demo.repository;

import com.example.demo.entity.JobNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobNoteRepository
        extends JpaRepository<JobNote, UUID> {
}