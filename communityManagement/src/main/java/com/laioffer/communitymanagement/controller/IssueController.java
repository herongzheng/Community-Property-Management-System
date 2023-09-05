package com.laioffer.communitymanagement.controller;

import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.web.bind.annotation.*;
import com.laioffer.communitymanagement.service.IssueService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

//    1. list issues
//    Issues will be listed in the following order:
//      notConfirmedIssues, confirmedNotClosedIssues and closedIssues
//    Within the notConfirmedIssues and confirmedNotClosedIssues,
//      the issues will be arranged in ascending order based on reportDate.
//    Within the closedIssues, the issues will be arranged in descending order based on closedDate.

//    1.0 for resident to list his/her issues-------------------------------------------------
//    1.0.1 version without principle
    @GetMapping(value = "/issues")
    public List<Issue> listIssues(@RequestParam(name = "username") String username) {
        return issueService.listIssuesByResident(username);
    }
//    1.0.2 version with principle
//    public List<Issue> listIssues(Principal principal) {
//        return issueService.listIssuesByResident(principal.getName());
//    }

//    1.1 for host to list all residents issues-------------------------------------------------
    @GetMapping(value = "hoa/issues")
    public List<Issue> listAllIssues() {
        return issueService.listAllIssues();
    }

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
