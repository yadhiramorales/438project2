package com.example.demo;

/**
 * JobApplicationTest
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 3/11/26 8:24 PM
 * @since 3/11/26
 */

import com.example.demo.entity.JobApplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class JobApplicationTest {

  @Test
  void jobApplicationFieldsSetCorrectly() {
    JobApplication job = new JobApplication(
        "Software Engineer",
        "Google",
        "Mountain View, CA",
        JobApplication.Status.APPLIED
    );

    assertEquals("Software Engineer", job.getJobTitle());
    assertEquals("Google", job.getCompany());
    assertEquals("Mountain View, CA", job.getLocation());
    assertEquals(JobApplication.Status.APPLIED, job.getStatus());
  }
}