package com.laioffer.communitymanagement;

import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.repository.IssueRepository;
import com.laioffer.communitymanagement.repository.UserRepository;
import com.laioffer.communitymanagement.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

@Component
public class DevelopmentTester implements ApplicationRunner {

    private static final Logger logger = LoggerFactory.getLogger(DevelopmentTester.class);
    private final UserRepository userRepository;
    private final IssueRepository issueRepository;
    private final IssueService issueService;

    public DevelopmentTester(UserRepository userRepository, IssueRepository issueRepository, IssueService issueService) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.issueService = issueService;
    }

    @Override
    public void run(ApplicationArguments args) {

//        logger.info("this user's name is " + user.getUsername() + " and the password is " + user.getPassword());

        User user = new User
                .Builder()
                .setUsername("haha")
                .setPassword("123")
                .setEnabled(true)
                .build()
                .setAptNumber("001")
                .setEmail("123@gmail.com")
                .setPhoneNumber("1111111")
                .setFirstName("ha")
                .setLastName("ha");
        userRepository.save(user);

        ZoneId zid = ZoneId.of("America/Los_Angeles");
        Issue issue = new Issue()
                .setContent("Plumbing issue")
                .setReportDate(LocalDate.now(zid))
                .setResident(new User.Builder().setUsername("haha").build());
        Issue issue2 = new Issue()
                .setContent("Plumbing issue2")
                .setReportDate(LocalDate.now(zid))
                .setResident(new User.Builder().setUsername("haha").build());
        issueRepository.save(issue);
        issueRepository.save(issue2);
        issueRepository.updateConfirmedByIssueId(1L, true);
        issueService.closeIssue(1L);
    }
}