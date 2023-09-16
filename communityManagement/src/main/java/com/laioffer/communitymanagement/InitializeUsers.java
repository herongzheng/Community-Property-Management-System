package com.laioffer.communitymanagement;

import com.laioffer.communitymanagement.authAndRegister.RegisterService;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.model.UserRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InitializeUsers implements ApplicationRunner {
    public final RegisterService registerService;

    private static final Logger logger = LoggerFactory.getLogger(InitializeUsers.class);

    public InitializeUsers(RegisterService registerService) {
        this.registerService = registerService;
    }
    @Override
    public void run(ApplicationArguments args) {
        // initialize with 5 residents
        for (int i = 1; i < 6; i++) {
            User user = new User().setUsername("Room00" + i).setPassword("123456").setAptNumber("Room00" + i)
                    .setEmail("Room00" + i + "@gmail.com")
                    .setFirstName("FirstName")
                    .setLastName("LastName")
                    .setPhoneNumber("1234567890");
            registerService.add(user, UserRole.ROLE_RESIDENT);
        }

        // initialize with 2 HOA employees
        List<String> HOAUsernames = new ArrayList<>();
        HOAUsernames.add("Ashley");
        HOAUsernames.add("Brad");
        for (int i = 0; i < 2; i++) {
            User user = new User().setUsername(HOAUsernames.get(i)).setPassword("123456").setAptNumber("Room000")
                    .setEmail(HOAUsernames.get(i) + "@gmail.com");
            registerService.add(user, UserRole.ROLE_HOA);
        }

        logger.info("5 residents and 2 HOA employees are created");
    }
}