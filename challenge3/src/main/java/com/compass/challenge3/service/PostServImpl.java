package com.compass.challenge3.service;

import com.compass.challenge3.client.ImportPosts;
import com.compass.challenge3.exceptions.Error;
import com.compass.challenge3.model.Comment;
import com.compass.challenge3.model.History;
import com.compass.challenge3.model.Post;
import com.compass.challenge3.model.PostStatus;
import com.compass.challenge3.repository.CommentRepository;
import com.compass.challenge3.repository.PostRepository;
import com.compass.challenge3.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServImpl implements PostService {

    private static PostRepository postRepository;

    private static ImportPosts postClient;

    private static CommentRepository commentRepository;



    public PostServImpl (PostRepository postRepository, ImportPosts postClient, CommentRepository commentRepository){
        this.postRepository = postRepository;
        this.postClient = postClient;
        this.commentRepository = commentRepository;
    }

    @Override
    public Post createPost(Long id) throws Error {
        validatePostId(id);
        Post postToCreate = postClient.getPostbyId(id);
        postToCreate.setCreateDate(LocalDate.now());
        postToCreate.setReprocessed(false);
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.CREATED);
        postToCreate.getHistory().add(history);
        return postRepository.save(postToCreate);
    }

    @Override
    public Post findPost(Long id) throws Error {
        validatePostId(id);
        Post postFinded = postRepository.findById(id).orElseThrow(
        );
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.POST_FIND);
        postFinded.getHistory().add(history);
        return postRepository.save(postFinded);
    }

    /*@Override
    public Post validatePostFound(Long id){
        validatePostId(id);
        Post postToValidate = postRepository.findById(id).orElseThrow(
                () -> new RegraNegocioException("Post not found")
        );
        if (getPostsById(id).get().getHistory().isEmpty()){
            History history = new History();
            history.setStatus(PostStatus.FAILED);
            history.setProcessDate(LocalDate.now());
            postToValidate.getHistory().add(history);
            return postRepository.save(postToValidate);
        }
        else{
            History history = new History();
            history.setId(history.getId());
            history.setProcessDate(LocalDate.now());
            history.setStatus(PostStatus.POST_OK);
            postToValidate.getHistory().add(history);
        }
        return postRepository.save(postToValidate);
    }*/
    @Override
    public Post validatePostFound(Long id) throws Error {
        validatePostId(id);
        Post postToValidate = postRepository.findById(id).orElseThrow(
        );
        History history = new History();
        if (postToValidate.getHistory().isEmpty()) {
            history.setStatus(PostStatus.FAILED);
            history.setProcessDate(LocalDate.now());
        } else {
            history.setId(history.getId());
            history.setProcessDate(LocalDate.now());
            history.setStatus(PostStatus.POST_OK);
        }
        postToValidate.getHistory().add(history);
        return postRepository.save(postToValidate);
    }
/*substituido ^*/

   /* @Override
    public Post findValidComments(Long id){
        Post postFounded = postRepository.findById(id).orElseThrow(
                () -> new RegraNegocioException("Post not found")
        );

        List<Comment> comments = saveComments(id);
        if(comments.isEmpty()){
            History history = new History();
            history.setProcessDate(LocalDate.now());
            history.setStatus(PostStatus.FAILED);
            postFounded.getHistory().add(history);
            return postRepository.save(postFounded);
        }
        else{
            History history = new History();
            history.setProcessDate(LocalDate.now());
            history.setStatus(PostStatus.COMMENTS_FIND);
            postFounded.getHistory().add(history);
        }
        return postRepository.save(postFounded);
    } */

    @Override
    public Post findValidComments(Long id) throws Error {
        Post postFounded = postRepository.findById(id).orElseThrow(
        );

        List<Comment> comments = saveComments(id);
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.COMMENTS_FIND);
        postFounded.getHistory().add(history);

        return postRepository.save(postFounded);
    }
/*^substituido*/

   /* @Override
    public Post setCommentsIntoPostAndValidate(Long id){
        Post post = getPostsById(id).orElseThrow(
                () -> new RegraNegocioException("Post not found")
        );
        List<Comment> listComments = saveComments(id);
        post.setComments(listComments);
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.COMMENTS_OK);
        post.getHistory().add(history);
        return postRepository.save(post);
    }*/
   @Override
   public Post setCommentsIntoPostAndValidate(Long id) throws Error {
       Post post = getPostsById(id).orElseThrow(
       );
       History history = new History();
       history.setProcessDate(LocalDate.now());
       history.setStatus(PostStatus.COMMENTS_OK);
       post.getHistory().add(history);
       return postRepository.save(post);
   }
/* substituido ^*/

   /* @Override
    public List<Comment> saveComments(Long postId) {
        validatePostId(postId);
        List<Comment> comments = postClient.getCommentbyId(postId);
        List<Comment> savedComments = new ArrayList<>();
        for (Comment comment : comments) {
            Comment savedComment = commentRepository.save(comment);
            savedComments.add(savedComment);
        }
        return savedComments;
    }*/
   @Override
   public List<Comment> saveComments(Long postId) throws Error {
       validatePostId(postId);
       return postClient.getCommentbyId(postId);
   }
/* substituido ^*/

    @Override
    public Post enablePost(Long id) throws Error {
        Post post = getPostsById(id).orElseThrow(
        );
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.ENABLED);
        post.getHistory().add(history);
        return postRepository.save(post);
    }

    @Override
    public Post disablePost(Long id) throws Error {
        Post post = getPostsById(id).orElseThrow(
        );
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.DISABLED);
        post.getHistory().add(history);
        return postRepository.save(post);
    }

    @Override
    public Post prepareToReprocessPost(Long id) throws Error {
        Post post = getPostsById(id).orElseThrow(
        );
        post.setReprocessed(true);
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.UPDATING);
        post.getHistory().add(history);
        return postRepository.save(post);
    }

    @Override
    public Post reprocessPost(Long id) throws Error {
        Post post = getPostsById(id).orElseThrow(
        );
        History history = new History();
        history.setProcessDate(LocalDate.now());
        history.setStatus(PostStatus.POST_FIND);
        post.getHistory().add(history);
        return postRepository.save(post);
    }

    @Override
    public void validatePostId(Long id) throws Error {
        if (id == null || id < 1 || id > 100) {
            throw new Error("Insert an ID between 1 and 100");
        }
    }

    @Override
    public Optional<Post> getPostsById(Long id) throws Error {
        validatePostId(id);
        Optional<Post> postFinded = postRepository.findById(id);
        return postFinded;
    }

  /*  @Override
    public List<Post> getAllPosts(int pageNo, int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postList = posts.getContent();
        return postList;
    }*/

    @Override
    public List<Post> getAllPosts(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.getContent();
    }
/* substituido ^*/

}