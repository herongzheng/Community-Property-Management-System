package com.laioffer.communitymanagement.issue;

public class IssueAlreadyClosedException extends RuntimeException {
    public IssueAlreadyClosedException(String message) {
        super(message);
    }
}
