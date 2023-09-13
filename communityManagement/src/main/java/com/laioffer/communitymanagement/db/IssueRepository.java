package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByResident_UsernameAndConfirmedFalseOrderByReportDateDesc(String username);
    List<Issue> findByResident_UsernameAndConfirmedTrueAndClosedDateIsNullOrderByReportDateDesc(String username);
    List<Issue> findByResident_UsernameAndClosedDateIsNotNullOrderByClosedDateDescReportDateDesc(String username);

    List<Issue> findByConfirmedFalseOrderByReportDateDesc();
    List<Issue> findByConfirmedTrueAndClosedDateIsNullOrderByReportDateDesc();
    List<Issue> findByClosedDateIsNotNullOrderByClosedDateDescReportDateDesc();

    @Query(value = "SELECT id FROM issue WHERE tenant_id = ?1", nativeQuery = true)
    List<Long> findIssueIdByUsername(String username);
    @Modifying
    @Query(value = "UPDATE Issue I SET I.confirmed = :confirmed WHERE I.id = :issueId")
    void updateConfirmedByIssueId(Long issueId, Boolean confirmed);

    @Modifying
    @Query(value = "UPDATE Issue I SET I.closedDate = :closedDate WHERE I.id = :issueId")
    void updateClosedDateByIssueId(Long issueId, LocalDate closedDate);
}