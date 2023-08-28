package com.laioffer.communitymanagement.service;

import com.laioffer.communitymanagement.exception.IssueNotExistException;
import com.laioffer.communitymanagement.db.entity.Issue;
import org.springframework.stereotype.Service;
import com.laioffer.communitymanagement.repository.IssueRepository;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class IssueService {
    private final IssueRepository issueRepository;


    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> listByGuest(String aptNumber) {
        return issueRepository.findByGuest_AptNumber(aptNumber);
    }

    public Issue listByIdAndGuest(Long issueId, String aptNumber) throws IssueNotExistException {
        Issue issue = issueRepository.findByIdAndGuest_AptNumber(issueId, aptNumber);
        if (issue == null) {
            throw new IssueNotExistException("Issue doesn't exist");
        }
        return issue;
    }

    public void add(Issue issue, MultipartFile[] images) {

    }

    public void closeIssue(Long issueId, String aptNumber) throws IssueNotExistException {
        Issue issue = issueRepository.findByIdAndGuest_AptNumber(issueId, aptNumber);
        if (issue == null) {
            throw new IssueNotExistException("Issue doesn't exist");
        }
        //.....
    }


}
