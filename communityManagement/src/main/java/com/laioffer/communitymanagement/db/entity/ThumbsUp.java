package com.laioffer.communitymanagement.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "thumbs_up")
public class ThumbsUp {
    @EmbeddedId
    private ThumbsUpKey id;
    @MapsId("post_id")
    @ManyToOne
    private Post post;

    @MapsId("user_username")
    @ManyToOne
    private User user;

    public ThumbsUp() {}

    public ThumbsUp(ThumbsUpKey id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }

    public ThumbsUpKey getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }

    public User getUser() {
        return user;
    }
}
