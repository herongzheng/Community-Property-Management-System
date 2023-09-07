package com.laioffer.communitymanagement.exception;

public class InvalidOldPasswordException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidOldPasswordException(String message) {
        super(message);
    }

    public InvalidOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }
}
