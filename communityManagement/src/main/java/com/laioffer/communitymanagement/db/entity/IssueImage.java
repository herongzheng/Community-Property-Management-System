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
    @JsonIgnore
    private Issue issue;

    //public constructor
    //private constructor with builder
    //getter and setter
    //class Builder
}
