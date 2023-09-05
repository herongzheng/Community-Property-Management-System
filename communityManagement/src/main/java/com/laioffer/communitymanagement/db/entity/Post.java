package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("posted_time")
    private LocalDateTime postedTime;
    private String content;
    @JsonProperty("stick_to_top")
    private boolean isImportant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long replyTo;

    private int likes;

    @JsonIgnore
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<ThumbsUp> thumbsUps;

    public LocalDateTime getPostedTime() {
        return postedTime;
    }

    public Post setPostedTime(LocalDateTime postedTime) {
        this.postedTime = postedTime;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;
    }

    public boolean isImportant() {
        return isImportant;
    }

    public Post setImportant(boolean important) {
        isImportant = important;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Post setUser(User user) {
        this.user = user;
        return this;
    }

    public Long getReplyTo() {
        return replyTo;
    }

    public Post setReplyTo(Long replyTo) {
        this.replyTo = replyTo;
        return this;
    }

    public int getLikes() {
        return likes;
    }

    public Post setLikes(int likes) {
        this.likes = likes;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Post setId(Long id) {
        this.id = id;
        return this;
    }
}
