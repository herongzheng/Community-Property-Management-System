package com.laioffer.communitymanagement.chat;

import com.laioffer.communitymanagement.db.PostRepository;
import com.laioffer.communitymanagement.db.ThumbsupRepository;
import com.laioffer.communitymanagement.db.UserRepository;
import com.laioffer.communitymanagement.db.entity.Post;
import com.laioffer.communitymanagement.db.entity.ThumbsUp;
import com.laioffer.communitymanagement.db.entity.ThumbsUpKey;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {
    private final PostRepository postRepository;
    private final ThumbsupRepository thumbsupRepository;
    private final UserRepository userRepository;
    public ChatService(PostRepository postRepository, ThumbsupRepository thumbsupRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.thumbsupRepository = thumbsupRepository;
        this.userRepository = userRepository;
    }

    // display all first-level posts; order the important post at the top and then newest to oldest
    public List<Post> displayPosts() {
        return postRepository.selectAllPostsOrdered();
    }

    // show replies of the corresponding parent post, newest to oldest
    public List<Post> displayReplies(Long postId) {
        return postRepository.selectAllRepliesOrdered(postId);
    }

    // like
    // @Transactional: save into thumbsup table and update post table the likes
    @Transactional
    public void addLikeToPost(Long postId, String username) {
        User user = userRepository.findByUsername(username);
        Post post = postRepository.getReferenceById(postId);

        ThumbsUp thumbsUp = new ThumbsUp(new ThumbsUpKey(postId, username),
                post, user);
        thumbsupRepository.save(thumbsUp);

        postRepository.addALike(postId);
    }

    // unlike
    // @Transactional: deleteById thumbsup table and update post table the likes
    @Transactional
    public void removeLikeFromPost(Long postId, String username) {
        thumbsupRepository.deleteByPost_idAndUser_username(postId, username);
        postRepository.removeALike(postId);
    }

    // in controller a post instance is built
    // create a post either a first-level post or a reply
    public void createAPost(Post post) {
        postRepository.save(post);
    }

    // delete a post or a reply
    // if this is a post, we also need to delete its replies,
    // Don't forget to remove the corresponding thumbsUps in thumbs_up table
    // I think DB cascade rules will do it for me
    @Transactional
    public void deleteAPost(Long postId) {
        postRepository.deleteById(postId);
        postRepository.deleteByReplyTo(postId);
    }

    // create a reply to a first-level post
    // in controller a post (reply in this case) instance is built
    // then call createAPost will do the job

    // delete a reply
//    public void deleteTheReply(Long replyId) {
//        postRepository.deleteById(replyId);
//    }

    // stick a post to the top
    // and return to display all posts
    // here it only return the first-level post, because only the
    // first-level post can be stick to top
    @Transactional
    public List<Post> topPost(Long postId) {
        postRepository.setImportance(postId);
        return postRepository.selectAllPostsOrdered();
    }

    // remove a post from the top
    // and return to display all posts
    // here it only return the first-level post, because only the
    // first-level post can be stick to top
    @Transactional
    public List<Post> untopPost(Long postId) {
        postRepository.removeImportance(postId);
        return postRepository.selectAllPostsOrdered();
    }
}
