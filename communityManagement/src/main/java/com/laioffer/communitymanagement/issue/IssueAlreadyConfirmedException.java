package com.laioffer.communitymanagement.issue;

public class IssueAlreadyConfirmedException extends RuntimeException {
    public IssueAlreadyConfirmedException(String message) {
        super(message);
    }
}
