package com.laioffer.communitymanagement.issue;

public class IssueNotExistException extends RuntimeException {
    public IssueNotExistException(String message) {
        super(message);
    }
}
