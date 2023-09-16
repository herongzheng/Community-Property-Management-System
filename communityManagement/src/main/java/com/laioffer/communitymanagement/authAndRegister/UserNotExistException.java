package com.laioffer.communitymanagement.authAndRegister;

public class UserNotExistException extends RuntimeException {
    public UserNotExistException(String message) {
        super(message);
    }
}
