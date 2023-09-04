package com.compass.challenge3.controller;

import com.compass.challenge3.exceptions.Error;
import com.compass.challenge3.model.History;
import com.compass.challenge3.model.Post;
import com.compass.challenge3.model.PostStatus;
import com.compass.challenge3.service.PostServImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/posts")
public class PostController {

    private PostServImpl postService;


    public PostController(PostServImpl postService){
        this.postService = postService;


    }

    @GetMapping
    public ResponseEntity<?> getAllPosts
            (@RequestParam (value = "pageNo", defaultValue = "0", required = false) int pageNo,
             @RequestParam(value = "pageSize", defaultValue = "10", required = false)int pageSize){
        List<Post> posts = postService.getAllPosts(pageNo, pageSize);
        Error errorResponse = new Error(
                "Post can not be disabled.");
        if(posts.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(posts);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id){
        try{
            Optional<Post> postToFind = postService.getPostsById(id);
            if(postToFind.isEmpty()){
                throw new Error("Not Found");
            }
            return ResponseEntity.status(HttpStatus.OK).body(postToFind);
        }
        catch(Error e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/{id}")
    public ResponseEntity<?> processPost(@PathVariable Long id) throws Error {
        Optional<Post> postToFind = postService.getPostsById(id);
        if(postToFind.isPresent()) {
            Post post = postToFind.get();
            List<History> historyList = post.getHistory();
            if (!historyList.isEmpty()) {
                History history = historyList.get(historyList.size() - 1);
                PostStatus status = history.getStatus();
                switch (status) {
                    case CREATED:
                        postService.findPost(id);
                        return ResponseEntity.status(HttpStatus.OK).body(post);
                    case POST_FIND:
                        postService.validatePostFound(id);
                        return ResponseEntity.status(HttpStatus.OK).body(post);
                    case POST_OK:
                        postService.findValidComments(id);
                        return ResponseEntity.status(HttpStatus.OK).body(post);
                    case COMMENTS_FIND:
                        postService.setCommentsIntoPostAndValidate(id);
                        return ResponseEntity.status(HttpStatus.OK).body(post);
                    case COMMENTS_OK:
                        postService.enablePost(id);
                        return ResponseEntity.status(HttpStatus.OK).body(post);
                    case ENABLED:
                        return ResponseEntity.status(HttpStatus.OK).body("Post enabled");
                    case DISABLED:
                        return ResponseEntity.status(HttpStatus.OK).body("Post disabled");
                    case FAILED:
                        postService.disablePost(id);
                        return ResponseEntity.status(HttpStatus.OK).body(post);
                    case UPDATING:
                        post.setHistory(new ArrayList<>());
                        post.setComments(new ArrayList<>());
                        postService.reprocessPost(id);
                        ResponseEntity.status(HttpStatus.OK).body(post);;
                }
            }
        }
        Post postCreated = postService.createPost(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(postCreated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> disablePost(@PathVariable Long id) {
        try {
            Optional<Post> postToFind = postService.getPostsById(id);
            if (postToFind.isPresent()) {
                Post post = postToFind.get();
                List<History> historyList = post.getHistory();
                History history = historyList.get(historyList.size() - 1);
                PostStatus status = history.getStatus();
                if (status == PostStatus.ENABLED || status == PostStatus.FAILED) {
                    Post postDisabled = postService.disablePost(id);
                    return ResponseEntity.status(HttpStatus.OK).body("Post disabled");
                }
            }
            throw new Error("Post can not be disabled.");
        }
        catch (Error e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> reprocessPost(@PathVariable Long id){
        try {
            Optional<Post> postToFind = postService.getPostsById(id);
            if (postToFind.isPresent()) {
                Post post = postToFind.get();
                List<History> historyList = post.getHistory();
                History history = historyList.get(historyList.size() - 1);
                PostStatus status = history.getStatus();
                if (status == PostStatus.ENABLED || status == PostStatus.DISABLED) {
                    Post postToPrepare = postService.prepareToReprocessPost(id);
                    return ResponseEntity.status(HttpStatus.OK).body(postToPrepare);
                }
                else if (status == PostStatus.UPDATING) {
                    post.setHistory(new ArrayList<>());
                    post.setComments(new ArrayList<>());
                    postService.reprocessPost(id);
                    return ResponseEntity.status(HttpStatus.OK).body(post);
                }
            }
            throw new Error("Post can not be reprocessed");
        }
        catch (Error e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}