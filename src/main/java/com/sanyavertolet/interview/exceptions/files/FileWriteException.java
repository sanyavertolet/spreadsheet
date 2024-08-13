package com.sanyavertolet.interview.exceptions.files;

public class FileWriteException extends FileException {
    @SuppressWarnings("unused")
    public FileWriteException(String message) {
        super(message);
    }

    @SuppressWarnings("unused")
    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
