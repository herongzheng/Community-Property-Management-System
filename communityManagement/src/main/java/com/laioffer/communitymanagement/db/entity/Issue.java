package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "issue")
@JsonDeserialize(builder = Issue.Builder.class)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @JsonProperty("posted_at")
    private LocalDate postedAt;

    @JsonProperty("closed_at")
    private LocalDate closedAt;

    @ManyToOne
    @JoinColumn(name = "guest_id")
    private User guest;

    public Issue() {}

    private Issue(Builder builder) {
        this.id = builder.id;
        this.content = builder.content;
        this.postedAt = builder.postedAt;
        this.closedAt = builder.closedAt;
        this.guest = builder.guest;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getPostedAt() {
        return postedAt;
    }

    public void setPostedAt(LocalDate postedAt) {
        this.postedAt = postedAt;
    }

    public LocalDate getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDate closedAt) {
        this.closedAt = closedAt;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public static class Builder {
        private Long id;

        private String content;

        @JsonProperty("posted_at")
        private LocalDate postedAt;

        @JsonProperty("closed_at")
        private LocalDate closedAt;

        private User guest;
        private Builder builder;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setPostedAt(LocalDate postedAt) {
            this.postedAt = postedAt;
            return this;
        }

        public Builder setClosedAt(LocalDate closedAt) {
            this.closedAt = closedAt;
            return this;
        }

        public Builder setGuest(User guest) {
            this.guest = guest;
            return this;
        }

        public Builder setBuilder(Builder builder) {
            this.builder = builder;
            return this;
        }

        public Issue build() {
            return new Issue(this);
        }

        public Issue createIssue() {
            return new Issue(builder);
        }
    }
}
