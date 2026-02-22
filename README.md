# Project 02 – JobTracker API

## Overview

JobTracker is a web-based backend application built using:

- Java  
- Spring Boot  
- Docker  
- Supabase (PostgreSQL)

**The purpose of this app is to allow users to seach jobs from
and external job API and be able to add notes associated
with the jobs they're interested in.**

The application demonstrates:

- REST API design  
- External API integration  
- Full CRUD functionality  
- Cloud-hosted database integration  
- Docker containerization  

---

## Pitch

**In today's competitive job market it is difficult to stay organized and structured. JobTracker is a centralized system to search job listings and store structured notes tied to a specific position.**

The system allows users to:

- Search for jobs via an external API  
- View job details  
- Add personal notes to selected jobs  
- Update notes  
- Delete notes  
- Retrieve saved notes  

This meets and satisfies complete CRUD functionality while integrating cloud database storage and containerized backend deployment.

---

## API Goal and Purpose

The goal of the API is to:

- Fetch job listings from a third-party job API  
- Allow users to attach notes to specific job listings  
- Persist those notes in a Supabase PostgreSQL database  
- Provide RESTful endpoints to manage those notes  

---

## Core Endpoints

- `GET /jobs/search`  
  Search jobs using external API  

- `GET /notes`  
  Retrieve all saved job notes  

- `POST /notes`  
  Create a new note for a job  

- `PUT /notes/{id}`  
  Update an existing note  

- `DELETE /notes/{id}`  
  Delete a note  

---

## Architecture

Client (Web Interface)  
→ Spring Boot REST API  
→ Supabase PostgreSQL  

Spring Boot also integrates with:

External Job API  

---

## User Stories

Each of the following will be created as individual GitHub issues.

### Story 1 – Search Jobs

**As a user**,  
I want to search for jobs by keyword,  
so that I can browse available opportunities.

- Priority: High  
- Difficulty: Medium  

---

### Story 2 – View Job Details

**As a user**,  
I want to view job details,  
so that I can evaluate whether I am interested.

- Priority: High  
- Difficulty: Medium  

---

### Story 3 – Add Note to Job

**As a user**,  
I want to add notes to a job listing,  
so that I can track application progress or impressions.

- Priority: High  
- Difficulty: Medium  

---

### Story 4 – Edit Note

**As a user**,  
I want to edit a saved note,  
so that I can update my job tracking information.

- Priority: Medium  
- Difficulty: Low  

---

### Story 5 – Delete Note

**As a user**,  
I want to delete a saved note,  
so that I can remove outdated information.

- Priority: Medium  
- Difficulty: Low  

---

### Story 6 – View All Notes

**As a user**,  
I want to view all saved job notes,  
so that I can track my applications in one place.

- Priority: High  
- Difficulty: Medium  

---

## Interaction and Interface

The application will be web-based.

- Backend: Spring Boot  
- Database: Supabase  
- Containerization: Docker  

Future improvements may include:

- Frontend implementation (React or similar)  
- User authentication  
- Deployment to a cloud hosting provider  

---

## Mockups

The mockups will include:

### 1. Job Search Page
- Search bar  
- List of returned jobs  
- Button to add note  

### 2. Job Detail Page
- Job title  
- Company  
- Location  
- Description  
- Note input field  

### 3. Notes Dashboard
- List of saved jobs  
- Edit button  
- Delete button  

Mockups can be created in Figma and linked in the repository.

---

## Entity Relationship Diagram (ERD)

### Primary Entity: JobNote

| Field        | Type        |
|--------------|------------|
| id           | UUID       |
| jobId        | String     |
| jobTitle     | String     |
| company      | String     |
| noteText     | Text       |
| createdAt    | Timestamp  |
| updatedAt    | Timestamp  |

### Optional Future Entity: User

| Field    | Type    |
|----------|--------|
| id       | UUID   |
| email    | String |
| password | String |

Relationship:

User (1) → (Many) JobNotes  

---

## Challenges

- Handling external API rate limits  
- Managing environment variables securely in Docker  
- Maintaining clean layered architecture  
- Error handling for API failures  
- Coordinating group development and merge conflicts  

---

## Required Resources

- Supabase database instance  
- Docker environment  
- External job API documentation  
- Spring Boot documentation  
- GitHub project board for issue tracking  

---

## Learning Outcomes Demonstrated

- REST API development  
- CRUD implementation  
- Database integration  
- Cloud database usage  
- Docker containerization  
- Issue-based development workflow  
