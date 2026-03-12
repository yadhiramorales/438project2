
/**
 * JobController
 *
 * @author Paulo Camacho
 * @version 1.0
 * @created 3/4/26 7:15 PM
 * @since 3/4/26
 */

package com.example.demo.controller;

import com.example.demo.entity.JobApplication;
import com.example.demo.repository.JobApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {

  @Autowired
  private JobApplicationRepository jobApplicationRepository;

  @GetMapping("/jobs")
  public List<JobApplication> getAllJobs() {
    return jobApplicationRepository.findAll();
  }
}