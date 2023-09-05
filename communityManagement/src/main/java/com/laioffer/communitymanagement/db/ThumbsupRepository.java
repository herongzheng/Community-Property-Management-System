package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.ThumbsUp;
import com.laioffer.communitymanagement.db.entity.ThumbsUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThumbsupRepository extends JpaRepository<ThumbsUp, ThumbsUpKey> {
    @Modifying
    @Query(value = "DELETE FROM thumbs_up WHERE post_id = ?1 and user_username = ?2", nativeQuery = true)
    void deleteByPost_idAndUser_username(Long postId, String username);
}
