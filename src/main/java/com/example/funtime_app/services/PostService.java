package com.example.funtime_app.services;

import com.example.funtime_app.dto.PopularNewTrendyPostDto;
import com.example.funtime_app.dto.PostDto;
import com.example.funtime_app.entity.Attachment;
import com.example.funtime_app.entity.Category;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.entity.User;
import com.example.funtime_app.interfaces.PostServiceInterface;
import com.example.funtime_app.mappers.PostMapper;
import com.example.funtime_app.projection.PopularNewTrendyPostProjection;
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

@RequiredArgsConstructor
@Service
public class PostService implements PostServiceInterface {
    private final AttachmentService attachmentService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    @Transactional
    @Override
    public HttpEntity<?> savePost(@Valid PostDto postDto) {
        MultipartFile file = postDto.file();
        Attachment attachment= attachmentService.saveAttachment(file);
        Optional<User> userOptional= userRepository.findById(postDto.userId());
        Optional<Category> categoryOptional = categoryRepository.findById(postDto.categoryId());
        if (attachment!=null&& userOptional.isPresent() && categoryOptional.isPresent()){
            Post post=Post.builder()
                    .title(postDto.title())
                    .description(postDto.description())
                    .category(categoryOptional.get())
                    .user(userOptional.get())
                    .build();
            Post savedPost = postRepository.save(post);
            PostDto postDto1 = postMapper.toDto(savedPost);
            return ResponseEntity.ok(postDto1);
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
    public ResponseEntity<?> getPopularPosts() {

        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getPopularPosts();
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("DDDDDDDD");
            e.printStackTrace();
           return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getNewPosts() {
        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getNewPosts();
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getTrendyPosts() {
        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getTrendyPosts();
            return ResponseEntity.ok(posts);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }
}
