package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.laioffer.communitymanagement.db.entity.Issue;


import javax.persistence.*;


@Entity
@Table(name = "issue_image")
public class IssueImage {


    @Id
    private String url;


    @ManyToOne
    @JoinColumn(name = "issue_id")
//    @JsonIgnore     //????
    private Issue issue;

    public IssueImage() {
    }


    public IssueImage(String url, Issue issue) {
        this.url = url;
        this.issue = issue;
    }


    public String getUrl() {
        return url;
    }


    public IssueImage setUrl(String url) {
        this.url = url;
        return this;
    }


    public Issue getStay() {
        return issue;
    }


    public IssueImage setStay(Issue issue) {
        this.issue = issue;
        return this;
    }
}
