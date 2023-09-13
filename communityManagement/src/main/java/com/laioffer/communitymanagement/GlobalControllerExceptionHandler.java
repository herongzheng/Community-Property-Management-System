package com.laioffer.communitymanagement;

import com.laioffer.communitymanagement.authAndRegister.UserAlreadyExistException;
import com.laioffer.communitymanagement.authAndRegister.UserNotExistException;
import com.laioffer.communitymanagement.changePassword.InvalidOldPasswordException;
import com.laioffer.communitymanagement.issue.IssueAlreadyClosedException;
import com.laioffer.communitymanagement.issue.IssueAlreadyConfirmedException;
import com.laioffer.communitymanagement.issue.IssueNotConfirmedException;
import com.laioffer.communitymanagement.issue.IssueNotExistException;
import com.laioffer.communitymanagement.packageAndMessage.PackageAlreadyPickedUpException;
import com.laioffer.communitymanagement.packageAndMessage.PackageNotExistException;
import com.laioffer.communitymanagement.packageAndMessage.ResidentNotFoundException;
import com.laioffer.communitymanagement.util.AmazonS3UploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(UserAlreadyExistException.class)
    public final ResponseEntity<String> handleUserAlreadyExistExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotExistException.class)
    public final ResponseEntity<String> handleUserNotExistExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InvalidOldPasswordException.class)
    public final ResponseEntity<String> handleInvalidOldPasswordException(InvalidOldPasswordException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(AmazonS3UploadException.class)
    public final ResponseEntity<String> handleAmazonS3UploadExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResidentNotFoundException.class)
    public final ResponseEntity<String> handleResidentNotFoundException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PackageNotExistException.class)
    public final ResponseEntity<String> handlePackageNotExistException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PackageAlreadyPickedUpException.class)
    public final ResponseEntity<String> handlePackageAlreadyPickedUpException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}