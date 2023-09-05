package com.laioffer.communitymanagement.controller;

import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.User;
import com.laioffer.communitymanagement.service.AuthorityService;
import org.springframework.web.bind.annotation.*;
import com.laioffer.communitymanagement.service.IssueService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class IssueController {
    private final IssueService issueService;
    private final AuthorityService authorityService;

    public IssueController(IssueService issueService, AuthorityService authorityService) {
        this.issueService = issueService;
        this.authorityService = authorityService;
    }

//    1. list issues
//    Issues will be listed in the following order:
//      notConfirmedIssues, confirmedNotClosedIssues and closedIssues
//    Within the notConfirmedIssues and confirmedNotClosedIssues,
//      the issues will be arranged in ascending order based on reportDate.
//    Within the closedIssues, the issues will be arranged in descending order based on closedDate.

//    1.0 version without principle (for test)
    @GetMapping(value = "/issues")
    public List<Issue> listIssues(String username) {
        String userRole = authorityService.findAuthorityByUsername(username);
        if (userRole.equals("ROLE_RESIDENT")) {
            return issueService.listIssuesByResident(username);
        }
        return issueService.listAllIssues();
    }

////    1.1 version with principle
//    @GetMapping(value = "/issues")
//    public List<Issue> listIssues(Principal principal) {
//        String username = principal.getName();
//        String userRole = authorityService.findAuthorityByUsername(username);
//        if (userRole.equals("ROLE_RESIDENT")) {
//            return issueService.listIssuesByResident(username);
//        }
//        return issueService.listAllIssues();
//    }



//    2. for resident to post issue--------------------------------------------------------
//    2.1 version without principle
    @PostMapping("/issues/create")
    public void addIssue(
            @RequestParam("content") String content,
            @RequestParam("images") MultipartFile[] images,
            @RequestParam("username") String username) {

        Issue issue = new Issue()
                .setContent(content)
                .setReportDate(LocalDate.now())
                .setResident(new User.Builder().setUsername(username).build());
        issueService.add(issue, images);
    }
//    2.2 version with principle
//    public void addIssue(
//            @RequestParam("content") String content,
//            @RequestParam("images") MultipartFile[] images,
//            Principal principle) {
//
//        Issue issue = new Issue()
//                .setContent(content)
//                .setReportDate(LocalDate.now())
//                .setResident(new User.Builder().setUsername(principle.getName()).build());
//        issueService.add(issue, images);
//    }

//    3. for host to confirm issue--------------------------------------------------------
    @PostMapping(value = "/issues/confirm/{issueId}")
    public void confirmIssue(@PathVariable Long issueId) {
        issueService.confirmIssue(issueId);
    }

//    4. for host to close issue--------------------------------------------------------
    @PostMapping(value = "/issues/close/{issueId}")
    public void closeIssue(@PathVariable Long issueId) {
        issueService.closeIssue(issueId);
    }

}
