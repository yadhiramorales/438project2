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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface JobApplicationRepository extends JpaRepository<JobApplication, UUID> {

	@Query("""
			SELECT j FROM JobApplication j
			WHERE (:keyword IS NULL OR LOWER(j.jobTitle) LIKE LOWER(CONCAT('%', :keyword, '%'))
				   OR LOWER(j.company) LIKE LOWER(CONCAT('%', :keyword, '%')))
			  AND (:location IS NULL OR LOWER(j.location) LIKE LOWER(CONCAT('%', :location, '%')))
			ORDER BY j.createdAt DESC
			""")
	List<JobApplication> search(@Param("keyword") String keyword, @Param("location") String location);
}