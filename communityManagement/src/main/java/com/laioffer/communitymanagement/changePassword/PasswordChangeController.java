package com.laioffer.communitymanagement.changePassword;

import com.laioffer.communitymanagement.exception.FailedToUpdateException;
import com.laioffer.communitymanagement.model.PasswordChangeBody;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PasswordChangeController {

    @Autowired
    private PasswordChangeService passwordChangeService;

    @PostMapping("/change_password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeBody passwordChangeBody) {
        boolean success = passwordChangeService.changePassword(passwordChangeBody);

        if (success) {
            return ResponseEntity.ok("Password and details updated successfully.");
        } else {
            throw new FailedToUpdateException("Failed to update password and details.");
        }
    }
}
