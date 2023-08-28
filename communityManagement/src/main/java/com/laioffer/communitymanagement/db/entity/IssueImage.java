package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "issue_image")
public class IssueImage {
    @Id
    private String url;
    @ManyToOne
    @JoinColumn(name = "issue_id")
    @JsonIgnore
    private Issue issue;

    public IssueImage() {}

    public String getUrl() {
        return url;
    }

    public Issue getIssue() {
        return issue;
    }

    public IssueImage setIssueId(Issue issue) {
        this.issue = issue;
        return this;
    }
}
