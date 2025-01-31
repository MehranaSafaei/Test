package org.example.mehrana.exception;

public class DuplicateNationalCodeException extends Exception {
    public DuplicateNationalCodeException() {
        super("National code already exists. Cannot save.");
    }
}
