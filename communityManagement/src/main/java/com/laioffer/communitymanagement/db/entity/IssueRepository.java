package com.laioffer.communitymanagement.db.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    @Query(value = "SELECT id FROM issue WHERE tenant_id = ?1", nativeQuery = true)
    List<Long> findIssueIdByUsername(String username);
}
