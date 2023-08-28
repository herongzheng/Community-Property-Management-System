package com.laioffer.communitymanagement.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "post")
public class Post {
    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("postedTime")
    private LocalDateTime postedTime;
    private String content;
    @JsonProperty("stick_to_top")
    private boolean isImportant;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long replyTo;

    private int likes;

}
