package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.model.PasswordChangeBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;
@RestController
public class PasswordChangeController {

    public PasswordChangeController() {
    }

    @PostMapping("/change_password")
    @ResponseStatus(HttpStatus.OK)
    public boolean changePassword(@RequestBody PasswordChangeBody passwordChangeBody, Principal principal) {

//        need to check if the old password matches the record
//        String oldPS = passwordChangeBody.oldPassword();
//        if the old password is correct

//        change the password in the database and return true; the frontend will jump to the login page

//        if the old password is incorrect, return false; the frontend stays on this "password change page"
        return true;
    }

}
