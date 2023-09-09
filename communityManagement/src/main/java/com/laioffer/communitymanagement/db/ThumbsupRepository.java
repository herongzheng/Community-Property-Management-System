package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.ThumbsUp;
import com.laioffer.communitymanagement.db.entity.ThumbsUpKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ThumbsupRepository extends JpaRepository<ThumbsUp, ThumbsUpKey> {
    @Modifying
    @Query(value = "DELETE FROM thumbs_up WHERE post_id = ?1 AND user_username = ?2", nativeQuery = true)
    void deleteByPost_idAndUser_username(Long postId, String username);

//    @Query(value = "SELECT post_id FROM thumbs_up WHERE post_id in ?1 AND user_username = ?2", nativeQuery = true)
//    Set<Long> findLikedPostByUsername(Set<Long> postIds, String username);

    @Query(value = "SELECT post_id FROM thumbs_up WHERE user_username = ?1", nativeQuery = true)
    Set<Long> findLikedIds(String username);

}
