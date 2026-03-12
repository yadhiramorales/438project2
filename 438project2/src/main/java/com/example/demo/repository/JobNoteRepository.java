package com.example.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.JobNote;

public interface JobNoteRepository extends JpaRepository<JobNote, UUID> {
    List<JobNote> findByJobApplication_Id(UUID jobApplicationId);
}