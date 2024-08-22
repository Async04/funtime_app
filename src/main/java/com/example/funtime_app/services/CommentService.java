package com.example.funtime_app.services;

import com.example.funtime_app.dto.request.CommentDTO;

import com.example.funtime_app.dto.response.CommentResponseProjection;
import com.example.funtime_app.entity.*;
import com.example.funtime_app.interfaces.CommentServiceImpl;
import com.example.funtime_app.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final RateRepository rateRepository;
    private final UserRepository userRepository;

    @Override
    public HttpEntity<?> getComments(UUID postId) {
        List<CommentResponseProjection> comments = commentRepository.getAllPostComments(postId);
        if(comments.isEmpty()){
            return ResponseEntity.status(404).body("Comment not found");
        }
        return ResponseEntity.ok(comments);
    }



    @Override
    public HttpEntity<?> getChildComments(UUID parentCommentId) {
        List<CommentResponseProjection> comments = commentRepository.getAllReplyComment(parentCommentId);
        if (comments.isEmpty()){
            return ResponseEntity.status(404).body("Comments not found");
        }
        return ResponseEntity.ok(comments);
    }



    @Override
    @Transactional
    public HttpEntity<?> saveComment(CommentDTO commentDto) {
        try {
            Optional<Post> postOptional = postRepository.findById(commentDto.getPostId());
            Comment comment = null;
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                User user = null;
                Optional<User> userOptional =  userRepository.findById(commentDto.getUserId());
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                    comment = Comment.builder()
                            .body(commentDto.getBody())
                            .post(post)
                            .commentedBy(user)
                            .build();

                    if (commentDto.getParentCommentId() != null) {
                        comment.setParentCommentId(commentDto.getParentCommentId());
                        comment.setPost(null);
                    }
                    commentRepository.save(comment);
                    return ResponseEntity.ok("Comment saved successfully");
                }

                else {
                    return ResponseEntity.status(404).body("User not found!!!");
                }

            }
            else {
                return ResponseEntity.status(404).body("Post not found!!!");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Comment not saved!!!");
        }
    }


}
