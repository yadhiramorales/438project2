package com.example.demo.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping(value = {"/jobs", "/jobs/search"}, produces = MediaType.TEXT_HTML_VALUE)
    public String jobsPage() {
        return "jobs"; // resolves to src/main/resources/templates/jobs.html
    }

    @GetMapping(value = "/notes", produces = MediaType.TEXT_HTML_VALUE)
    public String notesPage() {
        return "notes"; // resolves to src/main/resources/templates/notes.html
    }
}
