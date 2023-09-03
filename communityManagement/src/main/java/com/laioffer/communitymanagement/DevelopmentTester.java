package com.laioffer.communitymanagement;

import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.IssueImage;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.db.repository.IssueRepository;
import com.laioffer.communitymanagement.db.repository.UserRepository;
import com.laioffer.communitymanagement.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

        Issue issue = new Issue()
                .setContent("Plumbing issue")
                .setReportDate(LocalDate.now())
                .setResident(new User.Builder().setUsername("haha").build());
        String url = "https://myawsbucketforcpms.s3.us-east-2.amazonaws.com/1.jpeg";
        String url2 = "https://myawsbucketforcpms.s3.us-east-2.amazonaws.com/3.jpeg";
        IssueImage issueImage = new IssueImage(url, issue);
        IssueImage issueImage2 = new IssueImage(url2, issue);
        List<IssueImage> issueImages = new ArrayList<>();
        issueImages.add(issueImage);
        issueImages.add(issueImage2);
        issue.setImages(issueImages);
        issueRepository.save(issue);
        issueRepository.updateConfirmedByIssueId(1L, true);
        issueService.closeIssue(1L);

        Issue issue2 = new Issue()
                .setContent("Plumbing issue2")
                .setReportDate(LocalDate.now())
                .setResident(new User.Builder().setUsername("haha").build());
        issueRepository.save(issue2);

        User user2 = new User
                .Builder()
                .setUsername("ha")
                .setPassword("123")
                .setEnabled(true)
                .build()
                .setAptNumber("002")
                .setEmail("122@gmail.com")
                .setPhoneNumber("2222222")
                .setFirstName("h")
                .setLastName("a");
        userRepository.save(user2);

        Issue issue3 = new Issue()
                .setContent("Plumbing issue3")
                .setReportDate(LocalDate.now())
                .setConfirmed(true)
                .setResident(new User.Builder().setUsername("ha").build());
        String url3 = "https://myawsbucketforcpms.s3.us-east-2.amazonaws.com/4.jpeg";
        IssueImage issueImage3 = new IssueImage(url3, issue3);
        List<IssueImage> issueImages3 = new ArrayList<>();
        issueImages3.add(issueImage3);
        issue3.setImages(issueImages3);
        issueRepository.save(issue3);

        Issue issue4 = new Issue()
                .setContent("Plumbing issue4")
                .setReportDate(LocalDate.now().minusDays(1L))
                .setResident(new User.Builder().setUsername("ha").build());
        issueRepository.save(issue4);

        Issue issue5 = new Issue()
                .setContent("Plumbing issue5")
                .setReportDate(LocalDate.now().minusDays(1L))
                .setConfirmed(true)
                .setResident(new User.Builder().setUsername("ha").build());
        issueRepository.save(issue5);

        Issue issue6 = new Issue()
                .setContent("Plumbing issue6")
                .setReportDate(LocalDate.now().plusDays(1L))
                .setConfirmed(true)
                .setClosedDate(LocalDate.now().plusDays(1L))
                .setResident(new User.Builder().setUsername("haha").build());
        issueRepository.save(issue6);
    }
}