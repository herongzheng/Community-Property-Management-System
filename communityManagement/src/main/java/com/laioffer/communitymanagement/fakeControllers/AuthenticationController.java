package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.model.Token;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    public AuthenticationController() {
    }

    @PostMapping("/authenticate/resident")
    public Token authenticateGuest(@RequestBody User user) {
        return new Token("this is the fake token for resident");
    }

    @PostMapping("/authenticate/host")
    public Token authenticateHost(@RequestBody User user) {
        return new Token("this is the fake token for host");
    }
}
