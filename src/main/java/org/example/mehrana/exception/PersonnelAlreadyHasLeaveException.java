package org.example.mehrana.exception;

public class PersonnelAlreadyHasLeaveException extends Exception {
    public PersonnelAlreadyHasLeaveException() {
        super("This personnel already has an existing leave record.");
    }
}
