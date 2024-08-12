package com.example.funtime_app.services;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.PostServiceInterface;
import com.example.funtime_app.mappers.PostMapper;
import com.example.funtime_app.projection.PopularNewTrendyPostProjection;
import com.example.funtime_app.repository.AttachmentRepository;
import com.example.funtime_app.repository.CategoryRepository;
import com.example.funtime_app.repository.PostRepository;
import com.example.funtime_app.repository.UserRepository;
import com.github.fashionbrot.annotation.Valid;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PostService implements PostServiceInterface {
    private final AttachmentService attachmentService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final AttachmentRepository attachmentRepository;

    @Transactional
    @Override
    public HttpEntity<?> savePost(@Valid PostDTO postDto) {
        Optional<Attachment> attachment = attachmentRepository.findById(postDto.attachmentId());
        Optional<User> userOptional= userRepository.findById(postDto.userId());
        Optional<Category> categoryOptional = categoryRepository.findById(postDto.categoryId());
        if (attachment.isPresent() && userOptional.isPresent() && categoryOptional.isPresent()){
            Post post=Post.builder()
                    .attachment(attachment.get())
                    .title(postDto.title())
                    .description(postDto.description())
                    .category(categoryOptional.get())
                    .user(userOptional.get())
                    .build();
            Post savedPost = postRepository.save(post);
            PostDTO postDTO1 = postMapper.toDto(savedPost);
            return ResponseEntity.ok(postDTO1);
        }
        String errorMessage = "Failed to create post. ";
        if (attachment == null) errorMessage += "Attachment could not be saved. ";
        if (userOptional.isEmpty()) errorMessage += "User not found. ";
        if (categoryOptional.isEmpty()) errorMessage += "Category not found. ";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage.trim());
    }

    @Override
    public Page<Post> getPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date").descending());
        return postRepository.findAll(pageable);
    }

    @Override
    public ResponseEntity<?> getPopularPosts(int page, int size) {

        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getPopularPosts(page, size);
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
           return ResponseEntity.status(500).body(new RuntimeException("popular post not found"));
        }
    }

    @Override
    public ResponseEntity<?> getNewPosts(int page, int size) {
        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getNewPosts((page+1)*size, size);
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getTrendyPosts(int page, int size) {
        try {


            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getTrendyPosts((page+1)*size, size);
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getTopPosts(int page, int size) {

        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getTopPosts((page+1)*size, size);
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }


    }

    @Override
    public HttpEntity<?> getByCategoryId(UUID categoryId) {
        try {
            List<PopularNewTrendyPostProjection> posts = postRepository.getByCategoryId(categoryId);

            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }

    }

    @Override
    public HttpEntity<?> getSearchedPosts(String search) {
        try {
            List<Post> posts = postRepository.findAllByTitleContainingIgnoreCase(search);
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getUserAllPosts(UUID userId) {

       List<PopularNewTrendyPostProjection> userPosts =
               postRepository.getAllUserPostsByUserId(userId);
       return ResponseEntity.ok(userPosts);

    }


}
