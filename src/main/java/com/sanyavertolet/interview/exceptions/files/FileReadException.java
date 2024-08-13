package com.sanyavertolet.interview.exceptions.files;

public class FileReadException extends FileException {
    @SuppressWarnings("unused")
    public FileReadException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public FileReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
