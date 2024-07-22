package com.example.funtime_app.services;

import com.example.funtime_app.dto.CommentDto;
import com.example.funtime_app.dto.SendCommentDto;
import com.example.funtime_app.entity.*;
import com.example.funtime_app.interfaces.CommentServiceImpl;
import com.example.funtime_app.mappers.SendCommentMapper;
import com.example.funtime_app.repository.*;
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
        List<Comment> comments = commentRepository.findAllParentCommentId(parentCommentId);
        return getCommentDtos(comments);
    }



    @Override
    public HttpEntity<?> saveComment(CommentDto commentDto, UUID parentCommentId) {
        try {
            Optional<Post> postOptional = postRepository.findById(commentDto.getPostId());
            Comment comment = null;
            if (postOptional.isPresent()) {
                Post post = postOptional.get();
                User user = null;
                Optional<User> userOptional =  userRepository.findById(commentDto.getUserId());
                if (userOptional.isPresent()) {
                    user = userOptional.get();
                }
                Rate rate = Rate.builder()
                        .ratedBy(user)
                        .post(post)
                        .markValue(commentDto.getRateMarkValue())
                        .build();
                rateRepository.save(rate);
                comment = Comment.builder()
                        .body(commentDto.getBody())
                        .post(post)
                        .commentedBy(user)
                        .rate(rate)
                        .build();
            }
            if (comment != null) {
                if (parentCommentId != null) {
                    comment.setParentCommentId(parentCommentId);
                }
                commentRepository.save(comment);
            }
            return ResponseEntity.ok(commentDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Comment not saved!!!");
        }
    }

    private ResponseEntity<List<SendCommentDto>> getCommentDtos(List<Comment> comments) {
        List<SendCommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            if (comment.getParentCommentId() == null) {
                SendCommentDto sendCommentDto = sendCommentMapper.toDto(comment);
                commentDtos.add(sendCommentDto);
            }
        }
        return ResponseEntity.ok(commentDtos);
    }
}
