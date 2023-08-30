package com.laioffer.communitymanagement;

import com.laioffer.communitymanagement.db.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DevelopmentTester implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);

    User user = new User.Builder().setUsername("haha").setPassword("123").build();
    @Override
    public void run(ApplicationArguments args) {

        logger.info("this user's name is " + user.getUsername() + " and the password is " + user.getPassword());
    }
}