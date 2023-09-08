package com.laioffer.communitymanagement.changePassword;

public class InvalidOldPasswordException extends RuntimeException {
    public InvalidOldPasswordException(String message) {
        super(message);
    }

}
