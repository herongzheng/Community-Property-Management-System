package com.laioffer.communitymanagement.changePassword;

import com.laioffer.communitymanagement.model.PasswordChangeBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
public class PasswordChangeController {

    @Autowired
    private PasswordChangeService passwordChangeService;

    @PostMapping("/change_password")
    public void changePassword(@RequestBody PasswordChangeBody passwordChangeBody, Principal principal) {
        passwordChangeService.changePassword(passwordChangeBody, principal.getName());
    }
}
