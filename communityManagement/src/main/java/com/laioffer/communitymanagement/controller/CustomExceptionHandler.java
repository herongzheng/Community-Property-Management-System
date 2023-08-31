package com.laioffer.communitymanagement.controller;

import com.laioffer.communitymanagement.exception.IssueAlreadyClosedException;
import com.laioffer.communitymanagement.exception.IssueAlreadyConfirmedException;
import com.laioffer.communitymanagement.exception.IssueNotConfirmedException;
import com.laioffer.communitymanagement.exception.IssueNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(IssueNotExistException.class)
    public final ResponseEntity<String> handleIssueNotExistExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IssueAlreadyConfirmedException.class)
    public final ResponseEntity<String> handleIssueAlreadyConfirmedExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IssueAlreadyClosedException.class)
    public final ResponseEntity<String> handleIssueAlreadyClosedExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IssueNotConfirmedException.class)
    public final ResponseEntity<String> handleIssueNotConfirmedExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
