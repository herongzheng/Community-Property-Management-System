package com.laioffer.communitymanagement.changePassword;

import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.db.UserRepository;
import com.laioffer.communitymanagement.exception.InvalidOldPasswordException;
import com.laioffer.communitymanagement.exception.UserNotExistException;
import com.laioffer.communitymanagement.model.PasswordChangeBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordChangeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean changePassword(PasswordChangeBody passwordChangeBody) {

        User existingUser = userRepository.findByUsername(passwordChangeBody.username());
        if (existingUser == null) {
            throw new UserNotExistException("User does not exist.");
        }

        // encode new password and compare with old password
        if (!passwordEncoder.matches(passwordChangeBody.oldPassword(), existingUser.getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password.");
        }

        // update password
        existingUser.setPassword(passwordEncoder.encode(passwordChangeBody.newPassword()));

        // Decide on the values to update based on null checks
        String newPassword = passwordEncoder.encode(passwordChangeBody.newPassword());
        String email = passwordChangeBody.email() != null ? passwordChangeBody.email() : existingUser.getEmail();
        String phoneNumber = passwordChangeBody.phoneNumber() != null ? passwordChangeBody.phoneNumber() : existingUser.getPhoneNumber();
        String firstName = passwordChangeBody.firstName() != null ? passwordChangeBody.firstName() : existingUser.getFirstName();
        String lastName = passwordChangeBody.lastName() != null ? passwordChangeBody.lastName() : existingUser.getLastName();

        // Update user details in database
        userRepository.updateUserDetails(
                passwordChangeBody.username(),
                newPassword,
                email,
                phoneNumber,
                firstName,
                lastName
        );

        return true;
    }
}
