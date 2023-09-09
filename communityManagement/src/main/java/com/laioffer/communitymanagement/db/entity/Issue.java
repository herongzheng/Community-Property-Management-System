package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

/*
* The issue is submitted by any resident and received by HOA
* HOA manages the issues by considering whether it's confirmed and/or finished
*
* When an issue with content is submitted, the server doesn't get id and
* the confirmed filed should be false. When one issue record is loaded into the
* frontend, id will not be displayed and the List<IssueImage> will be displayed
* with other fields.
* */
@Entity
@Table(name = "issue")
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    @JsonProperty("report_date")
    private LocalDate reportDate;
    @JsonProperty("closed_date")
    private LocalDate closedDate;
    private boolean confirmed;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private User resident;


    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<IssueImage> images;
    public Issue() {}

    public Issue(Long id, String content, LocalDate reportDate, LocalDate closedDate, boolean confirmed, User resident, List<IssueImage> images) {
        this.id = id;
        this.content = content;
        this.reportDate = reportDate;
        this.closedDate = closedDate;
        this.confirmed = confirmed;
        this.resident = resident;
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getReportDate() {
        return reportDate;
    }

    public LocalDate getClosedDate() {
        return closedDate;
    }

    public Issue setClosedDate(LocalDate closedDate) {
        this.closedDate = closedDate;
        return this;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public Issue setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
        return this;
    }

    public User getResident() {
        return resident;
    }
}
