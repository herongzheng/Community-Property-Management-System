package com.laioffer.communitymanagement.authAndRegister;

import com.laioffer.communitymanagement.model.Token;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.model.UserRole;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/authenticate/resident")
    public Token authenticateGuest(@RequestBody User user) {
        return authenticationService.authenticate(user, UserRole.ROLE_RESIDENT);
    }

    @PostMapping("/authenticate/hoa")
    public Token authenticateHost(@RequestBody User user) {
        return authenticationService.authenticate(user, UserRole.ROLE_HOA);
    }

}
