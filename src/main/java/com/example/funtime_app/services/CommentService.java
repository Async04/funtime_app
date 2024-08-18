package com.example.funtime_app.services;

import com.example.funtime_app.dto.CommentDTO;

import com.example.funtime_app.dto.SendCommentDTO;
import com.example.funtime_app.entity.*;
import com.example.funtime_app.interfaces.CommentServiceImpl;
import com.example.funtime_app.mappers.SendCommentMapper;
import com.example.funtime_app.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService implements CommentServiceImpl {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final SendCommentMapper sendCommentMapper;
    private final RateRepository rateRepository;
    private final UserRepository userRepository;

    @Override
    public HttpEntity<?> getComments(UUID postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return getCommentDtos(comments);
    }



    @Override
    public HttpEntity<?> getChildComments(UUID parentCommentId) {
        List<Comment> comments = commentRepository.findAllByParentCommentId(parentCommentId);
        return getCommentDtos(comments);
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
                    }
                    commentRepository.save(comment);
                    return ResponseEntity.ok("Comment saved successfully");
                }

                else {
                    return ResponseEntity.badRequest().body("User not found!!!");
                }

            }
            else {
                return ResponseEntity.badRequest().body("Post not found!!!");
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Comment not saved!!!");
        }
    }

    private ResponseEntity<List<SendCommentDTO>> getCommentDtos(List<Comment> comments) {
        List<SendCommentDTO> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getParentCommentId() == null) {
                SendCommentDTO sendCommentDto = sendCommentMapper.toDto(comment);
                commentDtos.add(sendCommentDto);
            }
        }
        return ResponseEntity.ok(commentDtos);
    }
}
