package org.example.mehrana.exception;

public class UpdateException extends Exception{
    public UpdateException() {
        super("Error updating personnel. Please try again.");
    }
}
