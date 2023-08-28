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

    @GetMapping(value = "/issues")
    public List<Issue> listIssues(String aptNumber) {
        return issueService.listByGuest(aptNumber);
    }
//    public List<Issue> listIssues(Principal principal) {
//        return issueService.listByUser(principal.getAptNumber());
//    }

    @GetMapping(value = "/issues/{issueId}")
    public Issue getIssue(@PathVariable Long issueId, String aptNumber) {
        return issueService.listByIdAndGuest(issueId, aptNumber);
    }
//    public Issue getIssue(@PathVariable Long issueId, Principal principal) {
//        return issueService.listByIdAndGuest(issueId, principal.getAptNumber());
//    }

    @PostMapping("/issues")
    public void addIssue(
            @RequestParam("content") String content,
            @RequestParam("posted_at") LocalDate postedAt,  //how to get current date
            @RequestParam("images") MultipartFile[] images,
            Principal principal) {


        Issue issue = new Issue.Builder()
                .setContent(content)
                .setPostedAt(postedAt)
                .setGuest(new User.Builder().setUsername(principal.getName()).build())
                .build();
        issueService.add(issue, images);
    }

    @PutMapping(value = "/issues/{issueId}/close")
    public void closeIssue(@PathVariable Long issueId, String aptNumber) {
        issueService.closeIssue(issueId, aptNumber);
    }



}
