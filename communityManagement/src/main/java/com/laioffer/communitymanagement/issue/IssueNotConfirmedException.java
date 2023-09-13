package com.laioffer.communitymanagement.issue;

public class IssueNotConfirmedException extends RuntimeException {
    public IssueNotConfirmedException(String message) {
        super(message);
    }
}
