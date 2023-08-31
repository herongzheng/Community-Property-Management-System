package com.laioffer.communitymanagement.exception;

public class IssueAlreadyConfirmedException extends RuntimeException {
    public IssueAlreadyConfirmedException(String message) {
        super(message);
    }
}
