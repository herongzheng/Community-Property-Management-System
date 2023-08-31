package com.laioffer.communitymanagement.controller;

import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.web.bind.annotation.*;
import com.laioffer.communitymanagement.service.IssueService;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

//    1. for host and resident to list a resident's issues-------------------------------------------------
    @GetMapping(value = "/issues")
    public List<Issue> listIssues(@RequestParam(name = "apt_number") String aptNumber) {
        return issueService.listIssuesByResident(aptNumber);
    }
//    public List<Issue> listIssues(Principal principal) {
//        return issueService.listByUser(principal.getAptNumber());
//    }

//    2. for resident to post issue--------------------------------------------------------
    @PostMapping("/issues/create")
    public void addIssue(
            @RequestParam("content") String content,
//            @RequestParam("images") MultipartFile[] images,
            @RequestParam("username") String username) {
//            Principal principal) {

        ZoneId zid = ZoneId.of("America/Los_Angeles");
        Issue issue = new Issue()
                .setContent(content)
                .setReportDate(LocalDate.now(zid))
                .setResident(new User.Builder().setUsername(username).build());     //fake user?????????????????
        issueService.add(issue);
//        issueService.add(issue, images);
    }

    //    3. for host to confirm issue--------------------------------------------------------
    @PostMapping(value = "/issues/confirm/{issueId}")
    public void confirmIssue(@PathVariable Long issueId) {
//            , Principal principal) {
        issueService.confirmIssue(issueId);
    }

    //    4. for host to close issue--------------------------------------------------------
    @PostMapping(value = "/issues/close/{issueId}")
    public void closeIssue(@PathVariable Long issueId) {
//            , Principal principal) {
        issueService.closeIssue(issueId);
    }

}
