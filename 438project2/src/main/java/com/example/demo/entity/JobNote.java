package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "job_notes",
        indexes = {
                @Index(name = "idx_job_notes_job_application_id", columnList = "job_application_id")
        }
)
public class JobNote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    //one job application can have many-to-one job notes
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "job_application_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_job_notes_job_application")
    )
    private JobApplication jobApplication;

    @Column(name = "note_text", columnDefinition = "text")
    private String noteText;

    @Column(name = "created_at", nullable = false)
    private OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private OffsetDateTime updatedAt;

    public JobNote() {}

    public JobNote(JobApplication jobApplication, String noteText) {
        this.jobApplication = jobApplication;
        this.noteText = noteText;
    }

    @PrePersist
    void onCreate() {
        this.createdAt = OffsetDateTime.now();
        this.updatedAt = this.createdAt;
    }

    @PreUpdate
    void onUpdate() {
        this.updatedAt = OffsetDateTime.now();
    }

    //getters and setters
    public UUID getId() { return id; }

    public JobApplication getJobApplication() { return jobApplication; }
    public void setJobApplication(JobApplication jobApplication) { this.jobApplication = jobApplication; }

    public String getNoteText() { return noteText; }
    public void setNoteText(String noteText) { this.noteText = noteText; }

    public OffsetDateTime getCreatedAt() { return createdAt; }
    public OffsetDateTime getUpdatedAt() { return updatedAt; }
}