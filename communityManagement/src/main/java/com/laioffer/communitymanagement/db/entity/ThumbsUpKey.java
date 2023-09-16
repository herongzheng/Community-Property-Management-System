package com.laioffer.communitymanagement.db.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Arrays;

@Embeddable
public class ThumbsUpKey implements Serializable {
    private Long post_id;
    private String user_username;

    private static final Logger logger = LoggerFactory.getLogger(ThumbsUpKey.class);
    public ThumbsUpKey() {}

    public ThumbsUpKey(Long post_id, String user_username) {
        this.post_id = post_id;
        this.user_username = user_username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ThumbsUpKey another = (ThumbsUpKey) o;
        return post_id.equals(another.post_id) &&
                user_username.equals(another.user_username);
    }

    @Override
    public int hashCode() {
//        return Objects.hash(post_id, username);
        return Arrays.deepHashCode(new Object[] {post_id, user_username});
    }

    public Long getPost_id() {
        return post_id;
    }

    public String getUser_username() {
        return user_username;
    }
}
