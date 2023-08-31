package com.laioffer.communitymanagement.service;

import com.laioffer.communitymanagement.exception.IssueAlreadyClosedException;
import com.laioffer.communitymanagement.exception.IssueAlreadyConfirmedException;
import com.laioffer.communitymanagement.exception.IssueNotConfirmedException;
import com.laioffer.communitymanagement.exception.IssueNotExistException;
import com.laioffer.communitymanagement.db.entity.Issue;
import org.springframework.stereotype.Service;
import com.laioffer.communitymanagement.repository.IssueRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    //    1. for host and resident to list a resident's issues-------------------------------------------------
    public List<Issue> listIssuesByResident(String aptNumber) {
        return issueRepository.findByResident_AptNumber(aptNumber);
    }

    //    2. for resident to post issue--------------------------------------------------------
    public void add(Issue issue) {      //deal with duplicate??????
//    public void add(Issue issue, MultipartFile[] images) {      //store image???????????????????????????
//        List<IssueImage> issueImages = Arrays.stream(images)
//                .filter(image -> !image.isEmpty())
//                .parallel()
//                .map(imageStorageService::save)
//                .map(mediaLink -> new IssueImage(mediaLink, issue))
//                .collect(Collectors.toList());
//        issue.setImages(issueImages);
        issueRepository.save(issue);
    }

    //    3. for host to confirm issue--------------------------------------------------------
    public void confirmIssue(Long issueId) throws IssueNotExistException, IssueAlreadyConfirmedException {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.equals(Optional.empty())) {
            throw new IssueNotExistException("Issue doesn't exist");
        }
        if (issue.get().isConfirmed()) {
            throw new IssueAlreadyConfirmedException("Issue is already confirmed");
        }
        issueRepository.updateConfirmedByIssueId(issueId, true);
    }

    //    4. for host to close issue--------------------------------------------------------
    public void closeIssue(Long issueId) throws IssueNotExistException, IssueAlreadyClosedException {
        Optional<Issue> issue = issueRepository.findById(issueId);
        if (issue.equals(Optional.empty())) {
            throw new IssueNotExistException("Issue doesn't exist");
        }
        if (issue.get().getClosedDate() != null) {
            throw new IssueAlreadyClosedException("Issue is already closed");
        }
        if (!issue.get().isConfirmed()) {
            throw new IssueNotConfirmedException("Issue cannot be closed");
        }
        ZoneId zid = ZoneId.of("America/Los_Angeles");
        issueRepository.updateClosedDateByIssueId(issueId, LocalDate.now(zid));
    }

    public Issue listByIdAndGuest(Long issueId, String aptNumber) throws IssueNotExistException {
        Issue issue = issueRepository.findByIdAndResident_AptNumber(issueId, aptNumber);
        if (issue == null) {
            throw new IssueNotExistException("Issue doesn't exist");
        }
        return issue;
    }



}