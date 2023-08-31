package com.laioffer.communitymanagement.exception;

public class IssueAlreadyClosedException extends RuntimeException {
    public IssueAlreadyClosedException(String message) {
        super(message);
    }
}
