package org.example.mehrana.exception;

public class PersonnelNotFoundException extends Exception {
    public PersonnelNotFoundException() {
        super("Invalid personnel. This national code does not exist.");
    }

    public PersonnelNotFoundException(String message) {
        super(message);
    }
}
