package com.laioffer.communitymanagement.residentManagement;


import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.laioffer.communitymanagement.db.UserRepository;

import java.util.List;

@Service
public class UserService {


    private UserRepository userRepository;


    public List<User> findAllResidents() {
        return userRepository.selectAllResidentsOrdered();
    }

    @Transactional
    public void deleteUserAndCreate(String username) {


        // Delete from User table
        userRepository.deleteByUsername(username);

        // Create new User in User table using builder pattern
        User newUser = new User.Builder()
                .setUsername(username)
                .setPassword("123456")
                .build();

        userRepository.save(newUser);
    }
}