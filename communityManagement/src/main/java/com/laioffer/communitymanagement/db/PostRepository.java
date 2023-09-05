package com.laioffer.communitymanagement.db;

import com.laioffer.communitymanagement.db.entity.Post;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // display all first-level posts; order the important post at the top and then newest to oldest
    @Query(value = "SELECT * FROM post WHERE reply_to is null order by is_important DESC, id DESC ", nativeQuery = true)
    List<Post> selectAllPostsOrdered();

    // show replies of the corresponding parent post
    @Query(value = "SELECT * FROM post WHERE reply_to = ?1 ORDER BY id DESC", nativeQuery = true)
    List<Post> selectAllRepliesOrdered(Long postId);


    // add a like
    @Modifying
//    @Query(value = "UPDATE post SET likes = (SELECT likes FROM post WHERE id = ?1) + 1 WHERE id = ?1", nativeQuery = true)
    @Query(value = "UPDATE post SET likes = (SELECT likes FROM (SELECT likes FROM post WHERE id = ?1) as p) + 1 WHERE id = ?1", nativeQuery = true)
    void addALike(Long postId);

    // remove a like
    @Modifying
    @Query(value = "UPDATE post SET likes = (SELECT likes FROM (SELECT likes FROM post WHERE id = ?1) as p) - 1 WHERE id = ?1", nativeQuery = true)
    void removeALike(Long postId);

    // create a post call .save in service

    // delete a post
    // deleteById of the post itself and all of its replies
    void deleteByReplyTo(Long postId);

    // stick a post to the top
    @Modifying
    @Query(value = "UPDATE post SET is_important = true WHERE id = ?1", nativeQuery = true)
    void setImportance(Long postId);

    // remove a post from the top
    @Modifying
    @Query(value = "UPDATE post SET is_important = false WHERE id = ?1", nativeQuery = true)
    void removeImportance(Long postId);


}
