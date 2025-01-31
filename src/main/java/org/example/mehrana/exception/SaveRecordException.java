package org.example.mehrana.exception;

public class SaveRecordException extends Exception{
    public SaveRecordException() {
        super("Error saving record, \"Cannot save personnel. Duplicate or invalid data.\" ");
    }
}
