package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class residentManagement {

    public residentManagement() {
    }

    @GetMapping("/manage/residents")
    public List<User> displayAllResidents() {
        // return all users whose atp_number is not 000
        User user1 = new User.Builder().setUsername("Room 001").setPassword("123").setEnabled(true).build();
        User user2 = new User.Builder().setUsername("Room 003").build();
        user2.setFirstName("Lily").setLastName("Kennedy");
        User user3 = new User.Builder().setUsername("Room 004").build();
        user3.setFirstName("John").setLastName("Smith").setAptNumber("004").setEmail("john.smith@gmail.com");
        return List.of(user1, user2, user3);
    }

    @GetMapping("/manage/residents/new_resident/{username}")
    public User deleteAndCreateUser(@PathVariable String username) {
//        delete the all the records of the resident whose primary key is username

//        create a record in the user table with this username and set the temporary password as 123456
//        at the same time the frontend should display the username and the temporary password on the screen
        return new User.Builder().setUsername(username).setPassword("123456").build();
    }
}
