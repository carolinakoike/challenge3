package com.compass.challenge3.repository;

import com.compass.challenge3.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}