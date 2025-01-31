package org.example.mehrana.exception;

public class DuplicateDataException extends Exception {
    public DuplicateDataException() {
        super("Data is duplicated.");
    }
}
