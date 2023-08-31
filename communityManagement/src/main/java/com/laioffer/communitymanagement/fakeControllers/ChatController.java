package com.laioffer.communitymanagement.fakeControllers;

import com.laioffer.communitymanagement.db.entity.Post;
import com.laioffer.communitymanagement.db.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    public ChatController() {
    }

    @GetMapping("/chat")
    public List<Post> displayAllPosts() {
        Post p1 = new Post().setId(1L)
                .setContent("this puppy is cute!")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 32))
                .setUser(new User.Builder().setUsername("Room 001").build())
                .setLikes(3);
        Post p2 = new Post().setId(2L)
                .setContent("yes, that's true")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 39))
                .setUser(new User.Builder().setUsername("Room 005").build())
                .setLikes(0)
                .setReplyTo(1L);
        Post p3 = new Post().setId(3L)
                .setContent("Where should I go to pick up my package?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 32))
                .setUser(new User.Builder().setUsername("Room 004").build())
                .setLikes(0);
        return List.of(p1, p3);
    }
    @GetMapping("/chat/show_replies/{postId}")
    public List<Post> displayReplies(@PathVariable long postId) {
//        backend query the post table and return all the replies to this postId
        Post p2 = new Post().setId(2L)
                .setContent("yes, that's true")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 39))
                .setUser(new User.Builder().setUsername("Room 005").build())
                .setLikes(0)
                .setReplyTo(1L);
        return List.of(p2);
    }

    /*
    * When any user/HOA clicks on the like icon, the frontend will update the like number
    * on the screen immediately; don't have to wait to get the response from the backend
    * Once the response is fetched by the frontend, it re-render the number of thumbs up.
    * */
    @PostMapping("/chat/like/{postId}")
    public int likeAPost(@PathVariable long postId, Principal principal) {
//        backend should update the like_amount field and add a record into the thumbsUp table
//        and return the current like number for the postId
        return 3;
    }
    /*
     * Once any user/HOA clicks to remove the like they did, the frontend will update the like number
     * on the screen immediately; don't have to wait to get the response from the backend
     * Once the response is fetched by the frontend, it re-render the number of thumbs up.
     * */
    @PostMapping("/chat/unlike/{postId}")
    public int removeTheLike(@PathVariable long postId, Principal principal) {
//        backend should update the like_amount field and remove a record in the thumbsUp table
//        and return the current thumbsup number for the postId
        return 2;
    }

    @PostMapping("/chat/create")
    public List<Post> create(@RequestBody Post post, Principal principal) {
//        backend will add a record into the post table and return all posts
//        List<Post> and frontend will display them
        Post p1 = new Post().setId(1L)
                .setContent("this puppy is cute!")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 32))
                .setUser(new User.Builder().setUsername("Room 001").build())
                .setLikes(3);
        Post p2 = new Post().setId(2L)
                .setContent("yes, that's true")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 39))
                .setUser(new User.Builder().setUsername("Room 005").build())
                .setLikes(0)
                .setReplyTo(1L);
        Post p3 = new Post().setId(3L)
                .setContent("Where should I go to pick up my package?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 32))
                .setUser(new User.Builder().setUsername("Room 004").build())
                .setLikes(0);
        Post p4 = new Post().setId(15L)
                .setContent("Need more dramas to binge on. Any funny ones?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 52))
                .setUser(new User.Builder().setUsername("Room 002").build())
                .setLikes(0);

        return List.of(p1, p3, p4);
    }

/* Users can only delete the posts made by themselves
* Once a users clicks on "delete", because frontend already has all the posts stored in the state,
*  you can just delete the corresponding record in data and re-render; at the same time, it sends
* a POST request to the backend to delete this comment and its replies*/
    @PostMapping("/chat/delete/{postId}")
    public void delete(@PathVariable long postId, Principal principal) {

    }

/* create a reply to the corresponding post, when the user clicks on "save" button, the frontend
* will update the state and display on the screen and then call the following API, get a List of
* replies to this comment, update the state and then display on UI again.
*/

    @PostMapping("/chat/reply/{postId}")
    public List<Post> reply(@PathVariable long postId, @RequestBody Post reply) {
//    add the reply into the post table and query all the replies that matches the postId
//        get them returned in a list
        Post p2 = new Post().setId(2L)
                .setContent("yes, that's true")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 39))
                .setUser(new User.Builder().setUsername("Room 005").build())
                .setLikes(0)
                .setReplyTo(1L);
        return List.of(p2);
    }


    @PostMapping("/chat/top/{postId}")
    public List<Post> top(@PathVariable long postId) {
        // backend query the database for only the first-level comments
        // and order the comments by isImportant and postId DESC
        // return a List of Post to the frontend
        Post p1 = new Post().setId(1L)
                .setContent("this puppy is cute!")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 32))
                .setUser(new User.Builder().setUsername("Room 001").build())
                .setLikes(3);
        Post p3 = new Post().setId(3L)
                .setContent("Where should I go to pick up my package?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 32))
                .setUser(new User.Builder().setUsername("Room 004").build())
                .setLikes(0);
        Post p4 = new Post().setId(15L)
                .setContent("Need more dramas to binge on. Any funny ones?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 52))
                .setUser(new User.Builder().setUsername("Room 002").build())
                .setLikes(0)
                .setImportant(true);

        return List.of(p4, p3, p1);
    }

    @PostMapping("/chat/untop/{postId}")
    public List<Post> untop(@PathVariable long postId) {
        // backend query the database for only the first-level comments
        // and order the comments by isImportant and postId DESC
        // return a List of Post to the frontend
        Post p1 = new Post().setId(1L)
                .setContent("this puppy is cute!")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 15, 32))
                .setUser(new User.Builder().setUsername("Room 001").build())
                .setLikes(3);
        Post p3 = new Post().setId(3L)
                .setContent("Where should I go to pick up my package?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 32))
                .setUser(new User.Builder().setUsername("Room 004").build())
                .setLikes(0);
        Post p4 = new Post().setId(15L)
                .setContent("Need more dramas to binge on. Any funny ones?")
                .setPostedTime(LocalDateTime.of(2023, Month.AUGUST, 28, 16, 22))
                .setUser(new User.Builder().setUsername("Room 002").build())
                .setLikes(0);

        return List.of(p3, p4, p1);
    }


}
