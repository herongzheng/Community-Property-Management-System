package com.laioffer.communitymanagement.chat;

import com.laioffer.communitymanagement.db.PostRepository;
import com.laioffer.communitymanagement.db.entity.Post;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    private final ChatService chatService;
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @GetMapping("/chat")
    public List<Post> displayAllPosts() {
        return chatService.displayPosts();
    }

    @GetMapping("/chat/show_replies/{postId}")
    public List<Post> displayAllReplies(@PathVariable long postId) {
//        backend query the post table and return all the replies to this postId
        return chatService.displayReplies(postId);
    }

    /*
    * When any user/HOA clicks on the like icon, the frontend will update the like number
    * on the screen immediately; don't have to wait to get the response from the backend
    * Once the response is fetched by the frontend, it re-render the number of thumbs up.
    * */
    @PostMapping("/chat/like/{postId}")
    public void likeAPost(@PathVariable long postId, Principal principal) {
//        backend should update the like_amount field and add a record into the thumbsUp table
//        and return the current like number for the postId
//        chatService.addLikeToPost(postId, principal.getName());
        chatService.addLikeToPost(postId, "Room001");
    }
    /*
     * Once any user/HOA clicks to remove the like they did, the frontend will update the like number
     * on the screen immediately; don't have to wait to get the response from the backend
     * Once the response is fetched by the frontend, it re-render the number of thumbs up.
     * */
    @PostMapping("/chat/unlike/{postId}")
    public void removeTheLike(@PathVariable long postId, Principal principal) {
//        backend should update the like_amount field and remove a record in the thumbsUp table
//        and return the current thumbsup number for the postId
//        chatService.removeLikeFromPost(postId, principal.getName());
        chatService.removeLikeFromPost(postId, "Room001");
    }

    @PostMapping("/chat/create")
    public List<Post> create(
            @RequestParam(name = "content") String content,
            @RequestParam(name = "posted_time") String postedStringTime,
            @RequestParam(name = "reply_to", required = false) Long replyTo,
            Principal principal) {
//        backend will add a record into the post table and return all posts
//        List<Post> and frontend will display them
        LocalDateTime postedTime = LocalDateTime.parse(postedStringTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = new Post()
                .setContent(content)
                .setPostedTime(postedTime)
//                .setUser(new User().setUsername(principal.getName()))
                .setUser(new User().setUsername("Room001"))
                .setImportant(false)
                .setLikes(0);
        chatService.createAPost(post);
        return chatService.displayPosts();
    }

/* Users can only delete the posts made by themselves
* Once a users clicks on "delete", because frontend already has all the posts stored in the state,
*  you can just delete the corresponding record in data and re-render; at the same time, it sends
* a POST request to the backend to delete this comment and its replies*/
    @DeleteMapping("/chat/delete/{postId}")
    public void delete(@PathVariable long postId, Principal principal) {
        chatService.deleteAPost(postId);
    }

/* create a reply to the corresponding post, when the user clicks on "save" button, the frontend
* will update the state and display on the screen and then call the following API, get a List of
* replies to this comment, update the state and then display on UI again.
*/

    @PostMapping("/chat/reply/{postId}")
    public List<Post> reply(
            @RequestParam(name = "content") String content,
            @RequestParam(name = "posted_time") String postedStringTime,
            @RequestParam(name = "reply_to", required = false) Long replyTo,
            Principal principal) {
//        backend will add a record into the post table and return all posts
//        List<Post> and frontend will display them
        LocalDateTime postedTime = LocalDateTime.parse(postedStringTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Post post = new Post()
                .setContent(content)
                .setPostedTime(postedTime)
//                .setUser(new User().setUsername(principal.getName()))
                .setUser(new User().setUsername("Room003"))
                .setImportant(false)
                .setLikes(0)
                .setReplyTo(replyTo);
        chatService.createAPost(post);
        return chatService.displayReplies(replyTo);
    }

//    @PostMapping("/chat/delete_reply/{replyId}")
//    public void deleteAReply(@PathVariable Long replyId) {
//        chatService.deleteTheReply(replyId);
//    }

    @PostMapping("/chat/top/{postId}")
    public List<Post> top(@PathVariable long postId) {
        // backend query the database for only the first-level comments
        // and order the comments by isImportant and postId DESC
        // return a List of Post to the frontend

        return chatService.topPost(postId);
    }

    @PostMapping("/chat/untop/{postId}")
    public List<Post> untop(@PathVariable long postId) {
        // backend query the database for only the first-level comments
        // and order the comments by isImportant and postId DESC
        // return a List of Post to the frontend

        return chatService.untopPost(postId);
    }


}
