package org.example.mehrana.exception;

public class SaveRecordException extends Exception{
    public SaveRecordException() {
        super("Error saving the personnel record. ");
    }

    public SaveRecordException(String message) {
        super(message);
    }
}
