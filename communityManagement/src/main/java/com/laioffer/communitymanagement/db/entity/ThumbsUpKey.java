package com.laioffer.communitymanagement.db.entity;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ThumbsUpKey implements Serializable {
    private Long post_id;
    private String username;

    public ThumbsUpKey() {}

    public ThumbsUpKey(Long post_id, String username) {
        this.post_id = post_id;
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThumbsUpKey another = (ThumbsUpKey) o;
        return Objects.equals(post_id, another.post_id) && Objects.equals(username, another.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(post_id, username);
    }

    public Long getPost_id() {
        return post_id;
    }

    public String getUsername() {
        return username;
    }
}
