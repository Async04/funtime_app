package com.example.funtime_app.services;

import com.example.funtime_app.dto.PostDTO;
import com.example.funtime_app.entity.Attachment;
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
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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
    private final TagService tagService;
    private final AttachmentRepository attachmentRepository;

    @SneakyThrows
    @Transactional
    @Override
    public ResponseEntity<?> savePost(@Valid PostDTO postDto) {

        if (postDto.getAttachmentId()!=null){

            Attachment attachment =
                    attachmentRepository.findById(postDto.getAttachmentId()).get();

            attachmentRepository.save(attachment);

            Optional<User> byId = userRepository.findById(postDto.getUserId());
            if (byId.isEmpty()){
                return ResponseEntity.status(401).body("User not found");
            }
            Post post = Post.builder()
                    .title(postDto.getTitle())
                    .description(postDto.getDescription())
                    .user(byId.get())
                    .tags(tagService.generateTags(postDto))
                    .views(0)
                    .attachment(attachment)
                    .category(categoryRepository.findById(postDto.getCategoryId()).get())
                    .build();

            postRepository.save(post);
            return ResponseEntity.ok("Post saved succesfully!!!");
        }

        return  ResponseEntity.badRequest().body("File cannot be null");
       }

    @Override
    public ResponseEntity<?> getPosts(int page, int size) {

        List<PopularNewTrendyPostProjection> posts = postRepository.getAllPosts((page+1)*size, size);
        return ResponseEntity.ok(posts);
    }

    @Cacheable(value = "popularPosts")
    @Override
    public ResponseEntity<?> getPopularPosts(int page, int size) {

        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getPopularPosts(page, size);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RuntimeException("popular post not found"));
        }
    }

    @Cacheable(value = "newPosts")
    @Override
    public ResponseEntity<?> getNewPosts(int page, int size) {
        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getNewPosts((page + 1) * size, size);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getTrendyPosts(int page, int size) {
        try {


            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getTrendyPosts((page + 1) * size, size);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Cacheable(value = "topPosts")
    @Override
    public ResponseEntity<?> getTopPosts(int page, int size) {

        try {
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.getTopPosts((page + 1) * size, size);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }


    }



    @Override
    public ResponseEntity<?> getByCategoryId(UUID categoryId, Integer page, Integer size) {

        try {
            List<PopularNewTrendyPostProjection> posts = postRepository.getAllPostsByCategoryId(categoryId, (page+1)*size, size);
            System.out.println(posts);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }

    }

    @Override
    public HttpEntity<?> getSearchedPosts(String search) {
        try {
            search="%"+search+"%";
            List<PopularNewTrendyPostProjection> posts =
                    postRepository.searchPosts(search);
            return ResponseEntity.ok(posts);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(new RuntimeException("not found"));
        }
    }

    @Override
    public ResponseEntity<?> getUserAllPosts(UUID userId) {

        List<PopularNewTrendyPostProjection> userPosts =
                postRepository.getAllUserPostsByUserId(userId);
        return ResponseEntity.ok(userPosts);

    }

    @Override
    public ResponseEntity<?> getAllTagsPost(UUID tagsId) {
        List<PopularNewTrendyPostProjection> posts = postRepository.getAllPostByTagsId(tagsId);
        return ResponseEntity.ok(posts);
    }
}
