package com.example.funtime_app.repository;

import com.example.funtime_app.entity.Post;
import com.example.funtime_app.projection.PopularNewTrendyPostProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query(nativeQuery = true, value = """

            select
                   p.id postId,
                   p.attachment_id postAttachmentId,
                   p.user_id,
                   u.profile_photo_id,
                   p.title,
                   p.description
               
                   from posts p
                   join users u on p.user_id=u.id
                   join attachment a on a.id=p.attachment_id
            order by p.views desc limit 10
               

                """)
    List<PopularNewTrendyPostProjection> getPopularPosts(int page, int size);


    @Query(nativeQuery = true, value = """
            select
                p.id post_id,
                p.attachment_id post_attachment_id,
                p.user_id,
                u.profile_photo_id,
                p.title,
                p.description
            
                from posts p
                join users u on p.user_id=u.id
                join attachment a on a.id=p.attachment_id
            
            order by p.created_at desc limit 10
            
               

                """)
    List<PopularNewTrendyPostProjection> getTrendyPosts();

    @Query(nativeQuery = true, value = """

            
            
            select
                p.id post_id,
                p.attachment_id post_attachment_id,
                p.user_id,
                u.profile_photo_id,
                p.title,
                p.description
            
                from posts p
                join users u on p.user_id=u.id
                join attachment a on a.id=p.attachment_id
            
            order by p.created_at desc , EXTRACT(DAY FROM p.created_at) desc limit 10
            
                """)
    List<PopularNewTrendyPostProjection> getNewPosts();

    ResponseEntity<?> getByCategoryId(UUID categoryId);
}