package com.laioffer.communitymanagement.issue;


import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @GetMapping(value = "/issues")
    public List<Issue> listIssues(Principal principal) {
        String username = principal.getName();
        UsernamePasswordAuthenticationToken UPAT = (UsernamePasswordAuthenticationToken) principal;
        String userRole = new ArrayList<>(UPAT.getAuthorities()).get(0).toString();
        if (userRole.equals("ROLE_RESIDENT")) {
            return issueService.listIssuesByResident(username);
        }
        return issueService.listAllIssues();
    }

    //    2. for resident to post issue
    @PostMapping("/issues/create")
    public void addIssue(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam(value = "images", required = false) MultipartFile[] images,
            Principal principal) {
        Issue issue = new Issue()
                .setTitle(title)
                .setContent(content)
                .setReportDate(LocalDate.now())
                .setResident(new User.Builder().setUsername(principal.getName()).build());
        if (images == null) {
            issueService.add(issue);
        } else {
            issueService.add(issue, images);
        }
    }

    //    3. for host to confirm issue

    @PostMapping(value = "/issues/confirm/{issueId}")
    public void confirmIssue(@PathVariable Long issueId) {
        issueService.confirmIssue(issueId);
    }

    //    4. for host to close issue
    @PostMapping(value = "/issues/close/{issueId}")
    public void closeIssue(@PathVariable Long issueId) {
        issueService.closeIssue(issueId);
    }

}

