package com.compass.challenge3.service;

import com.compass.challenge3.exceptions.Error;
import com.compass.challenge3.model.Comment;
import com.compass.challenge3.model.History;
import com.compass.challenge3.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService{

    Post createPost(Long id) throws Error;

    Post findPost(Long id) throws Error;

    Post validatePostFound(Long id) throws Error;

    Post findValidComments(Long id) throws Error;

    Post enablePost(Long id) throws Error;

    Post disablePost(Long id) throws Error;

    Post prepareToReprocessPost(Long id) throws Error;

    Post reprocessPost(Long id) throws Error;

    void validatePostId(Long id) throws Error;

    Optional<Post> getPostsById(Long id) throws Error;

    List<Post> getAllPosts(int pageNo, int pageSize);

    Post setCommentsIntoPostAndValidate(Long id) throws Error;

    List<Comment> saveComments(Long postId) throws Error;
}
