package com.laioffer.communitymanagement.issue;


import com.laioffer.communitymanagement.db.entity.IssueImage;
import com.laioffer.communitymanagement.db.entity.Issue;
import com.laioffer.communitymanagement.util.ImageUploadService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import com.laioffer.communitymanagement.db.IssueRepository;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

@Service
public class IssueService {
    private final IssueRepository issueRepository;

    private final ImageUploadService imageUploadService;

    public IssueService(IssueRepository issueRepository, ImageUploadService imageUploadService) {
        this.issueRepository = issueRepository;
        this.imageUploadService = imageUploadService;
    }

    //    1. list issues
//    1.0 for resident to list his/her issues-------------------------------------------------
    @Cacheable(value = "resident_issues", key = "#username")
    public List<Issue> listIssuesByResident(String username) {
        List<Issue> notConfirmedIssues = issueRepository.findByResident_UsernameAndConfirmedFalseOrderByReportDateDesc(username);
        List<Issue> confirmedNotClosedIssues = issueRepository.findByResident_UsernameAndConfirmedTrueAndClosedDateIsNullOrderByReportDateDesc(username);
        List<Issue> closedIssues = issueRepository.findByResident_UsernameAndClosedDateIsNotNullOrderByClosedDateDescReportDateDesc(username);
        List<Issue> allIssues = Stream.of(notConfirmedIssues, confirmedNotClosedIssues,closedIssues)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return allIssues;
    }

    //    1.1 for host to list a resident's issues-------------------------------------------------
    @Cacheable(value = "all_issues")
    public List<Issue> listAllIssues() {
        List<Issue> notConfirmedIssues = issueRepository.findByConfirmedFalseOrderByReportDateDesc();
        List<Issue> confirmedNotClosedIssues = issueRepository.findByConfirmedTrueAndClosedDateIsNullOrderByReportDateDesc();
        List<Issue> closedIssues = issueRepository.findByClosedDateIsNotNullOrderByClosedDateDescReportDateDesc();
        List<Issue> allIssues = Stream.of(notConfirmedIssues, confirmedNotClosedIssues,closedIssues)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return allIssues;
    }

    //    2. for resident to post issue--------------------------------------------------------
    @Caching(evict = {@CacheEvict(value = "resident_issues", key = "#issue.resident.username"), @CacheEvict(value = "all_issues", allEntries = true)})
    @Transactional
    public void add(Issue issue, MultipartFile[] images) {
        List<IssueImage> issueImages = Arrays.stream(images)
                .filter(image -> !image.isEmpty())
                .parallel()
                .map(imageUploadService::uploadImage)
                .map(mediaLink -> new IssueImage(mediaLink, issue))
                .collect(Collectors.toList());
        issue.setImages(issueImages);
        issueRepository.save(issue);
    }

    @Caching(evict = {@CacheEvict(value = "resident_issues", key = "#issue.resident.username"), @CacheEvict(value = "all_issues", allEntries = true)})
    public void add(Issue issue) {
        issueRepository.save(issue);
    }

    //    3. for host to confirm issue--------------------------------------------------------
    @Transactional
    @Caching(evict = {@CacheEvict(value = "resident_issues", allEntries = true), @CacheEvict(value = "all_issues", allEntries = true)})
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
    @Transactional
    @Caching(evict = {@CacheEvict(value = "resident_issues", allEntries = true), @CacheEvict(value = "all_issues", allEntries = true)})
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
        issueRepository.updateClosedDateByIssueId(issueId, LocalDate.now());
    }

}

