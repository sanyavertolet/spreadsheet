package com.sanyavertolet.interview.exceptions.files;

public class FileException extends Exception {
    @SuppressWarnings("unused")
    public FileException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public FileException(String message, Throwable cause) {
        super(message, cause);
    }
}
