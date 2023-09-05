package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    List<Issue> findByResident_UsernameAndConfirmedFalseOrderByReportDateAsc(String username);
    List<Issue> findByResident_UsernameAndConfirmedTrueAndClosedDateIsNullOrderByReportDateAsc(String username);
    List<Issue> findByResident_UsernameAndClosedDateIsNotNullOrderByClosedDateDesc(String username);

    List<Issue> findByConfirmedFalseOrderByReportDateAsc();
    List<Issue> findByConfirmedTrueAndClosedDateIsNullOrderByReportDateAsc();
    List<Issue> findByClosedDateIsNotNullOrderByClosedDateDesc();

    @Modifying
    @Query(value = "UPDATE Issue I SET I.confirmed = :confirmed WHERE I.id = :issueId")
    void updateConfirmedByIssueId(Long issueId, Boolean confirmed);

    @Modifying
    @Query(value = "UPDATE Issue I SET I.closedDate = :closedDate WHERE I.id = :issueId")
    void updateClosedDateByIssueId(Long issueId, LocalDate closedDate);

}
