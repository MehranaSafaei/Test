package org.example.mehrana.exception;

public class DeleteException extends Exception{
    public DeleteException() {
        super("Error deleting personnel. Please try again.");
    }
}
