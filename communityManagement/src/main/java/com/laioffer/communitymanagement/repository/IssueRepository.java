package com.laioffer.communitymanagement.repository;

import com.laioffer.communitymanagement.db.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByGuest_AptNumber(String aptNumber);

    Issue findByIdAndGuest_AptNumber(Long issueId, String aptNumber);

}
