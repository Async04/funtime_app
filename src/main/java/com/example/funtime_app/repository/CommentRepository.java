package com.example.funtime_app.repository;

import com.example.funtime_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByPostId(UUID postId);


    List<Comment> findAllByParentCommentId(UUID parentCommentId);
}