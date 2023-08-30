package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@RestController
public class IssueController {

    public IssueController() {
    }

    @GetMapping("/issues")
    public List<Issue> displayAllIssues() {
        Issue i1 = new Issue(1L, "the pipeline under the sink in the main bath is leaking",
                LocalDate.of(2023, Month.AUGUST, 28), LocalDate.now(), true,
                new User.Builder().setUsername("Room 001").build(), null);

        Issue i2 = new Issue(3L, "the air conditioner is not working",
                LocalDate.of(2023, Month.AUGUST, 29), LocalDate.now(), false,
                new User.Builder().setUsername("Room 005").build(), null);
        return List.of(i1, i2);
    }

    /* create and submit an issue by the resident
    * Once it's submitted, on the resident side the new issue will be displayed in the issue list
    * the HOA side can see the submitted issue as well*/
    @PostMapping("/issues/create")
    @ResponseStatus(HttpStatus.OK)
    public void create(@RequestParam("content") String content,
                       @RequestParam("report_date") LocalDate reportDate,
                       @RequestParam("images") MultipartFile[] images,
                       Principal principal) {

    }

    /*
    * confirm the issue is "confirmed" by HOA.
    * Once HOA clicks on the "confirm" button, both the resident side and the HOA side should reflect this update
     */
    @PostMapping("/issues/confirm")
    @ResponseStatus(HttpStatus.OK)
    public void confirm(long issueId) {
//        update the confirmed filed in issue record to be true
    }

    /*
    * mark this issue as closed
    * Once HOA clicks on the "close" button, the HOA side will pop up a window to make sure the HOA employee really
    * wants to close this issue "yes" or "no". If the employee clicks on "yes", this will be displayed on both
    * resident and HOA sides.
    */


    @PostMapping("/issues/close/{issueId}")
    @ResponseStatus(HttpStatus.OK)
    public void close(@PathVariable long issueId) {
//         update the corresponding issue record by filling in the closed_date with LocalDate.now()
    }


}
