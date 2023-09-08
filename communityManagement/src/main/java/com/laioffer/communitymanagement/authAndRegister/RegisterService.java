package com.laioffer.communitymanagement.authAndRegister;

import com.laioffer.communitymanagement.db.AuthorityRepository;
import com.laioffer.communitymanagement.db.UserRepository;
import com.laioffer.communitymanagement.db.entity.Authority;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.model.UserRole;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterService(UserRepository userRepository, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void add(User user, UserRole role) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        userRepository.save(user);
        authorityRepository.save(new Authority(user.getUsername(), role.name()));
    }


}
