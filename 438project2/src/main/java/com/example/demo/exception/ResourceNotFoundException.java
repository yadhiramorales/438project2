package com.example.demo.exception;

/** Simple 404-style exception for missing entities. */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
