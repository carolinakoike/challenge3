package com.compass.challenge3.controller;

import com.compass.challenge3.model.Post;
import com.compass.challenge3.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostImportService {

    private static final String API_URL = "https://jsonplaceholder.typicode.com/posts";

    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    public PostImportService(PostRepository postRepository, RestTemplate restTemplate) {
        this.postRepository = postRepository;
        this.restTemplate = restTemplate;
    }

    public void importPosts() {
        Post[] posts = restTemplate.getForObject(API_URL, Post[].class);

        for (Post post : posts) {
            postRepository.save(post);
        }
    }
}
