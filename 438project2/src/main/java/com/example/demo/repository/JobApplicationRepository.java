/**
 * JobApplicationRepository
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 3/4/26 7:17 PM
 * @since 3/4/26
 */


package com.example.demo.repository;

import com.example.demo.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {
}