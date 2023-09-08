package com.laioffer.communitymanagement.changePassword;

import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.db.UserRepository;
import com.laioffer.communitymanagement.model.PasswordChangeBody;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PasswordChangeService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public PasswordChangeService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    @Transactional
    public void changePassword(PasswordChangeBody passwordChangeBody, String username) throws InvalidOldPasswordException {
        User existingUser = userRepository.findByUsername(username);
        Authentication auth = null;

        // check whether the old password is correct
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, passwordChangeBody.oldPassword()));
        } catch (AuthenticationException exception) {
            throw new InvalidOldPasswordException("The old password is incorrect. Please try again!");
        }

        if (auth == null || !auth.isAuthenticated()) {
            throw new InvalidOldPasswordException("The old password is incorrect. Please try again!");
        }

        // depending on user's preference update the user's profile info
        String newPassword = passwordEncoder.encode(passwordChangeBody.newPassword());
        String firstName = passwordChangeBody.firstName() != null ? passwordChangeBody.firstName() : existingUser.getFirstName();
        String lastName = passwordChangeBody.lastName() != null ? passwordChangeBody.lastName() : existingUser.getLastName();
        String email = passwordChangeBody.email() != null ? passwordChangeBody.email() : existingUser.getEmail();
        String phoneNumber = passwordChangeBody.phoneNumber() != null ? passwordChangeBody.phoneNumber() : existingUser.getPhoneNumber();

        // Update user details in database
        userRepository.updateUserInfo(
                username,
                newPassword,
                email,
                phoneNumber,
                firstName,
                lastName
        );
    }
}
