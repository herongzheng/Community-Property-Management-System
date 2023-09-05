package com.laioffer.communitymanagement;

import com.laioffer.communitymanagement.db.PostRepository;
import com.laioffer.communitymanagement.db.entity.Post;
import com.laioffer.communitymanagement.db.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.Month;

@Component
public class DevelopmentTester implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);


    User user = new User.Builder().setUsername("haha").setPassword("123").build();

    Post p1 = new Post().setId(1L)
            .setContent("this puppy is cute!")
            .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 32))
            .setUser(new User.Builder().setUsername("Room 001").build())
            .setLikes(3);
    Post p2 = new Post().setId(2L)
            .setContent("yes, that's true")
            .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 39))
            .setUser(new User.Builder().setUsername("Room 005").build())
            .setLikes(0)
            .setReplyTo(1L);
    Post p3 = new Post().setId(3L)
            .setContent("Where should I go to pick up my package?")
            .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 32))
            .setUser(new User.Builder().setUsername("Room 004").build())
            .setLikes(0);
    Post p4 = new Post().setId(15L)
            .setContent("Need more dramas to binge on. Any funny ones?")
            .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 52))
            .setUser(new User.Builder().setUsername("Room 002").build())
            .setLikes(0);
    @Override
    public void run(ApplicationArguments args) {

        logger.info("this user's name is " + user.getUsername() + " and the password is " + user.getPassword());
    }
}