package com.example.funtime_app.repository;

import com.example.funtime_app.dto.response.OnePostDTO;
import com.example.funtime_app.entity.Post;
import com.example.funtime_app.projection.PopularNewTrendyPostProjection;
import com.example.funtime_app.projection.UserPostProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
            order by p.views desc limit :limit offset :offset 
               
                                
                """)
    List<PopularNewTrendyPostProjection> getPopularPosts(@Param("offset") int offset, @Param("limit") int limit);


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
                        
            order by p.created_at desc limit :limit offset :offset 
                        
               

                """)
    List<PopularNewTrendyPostProjection> getTrendyPosts(@Param("offset") int offset, @Param("limit") int limit);

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
                        
            order by p.created_at desc , EXTRACT(DAY FROM p.created_at) desc limit :limit offset :offset 
                        
                """)
    List<PopularNewTrendyPostProjection> getNewPosts(@Param("offset") int offset, @Param("limit") int limit);

    @Query(nativeQuery = true, value = """
        select
                 p.id post_id,
                 p.attachment_id post_attachment_id,
                 p.user_id,
                 u.profile_photo_id,
                 p.title,
                 p.description
                 from posts p
                 join users u on p.user_id = u.id
                 join attachment a on a.id = p.attachment_id
                 where p.category_id = :categoryId
                 order by p.created_at desc limit :limit offset :offset
""")
    List<PopularNewTrendyPostProjection> getAllPostsByCategoryId(@Param("categoryId") UUID categoryId, @Param("offset") int offset, @Param("limit") int limit );


    @Query(nativeQuery = true, value = """
            SELECT
                p.id AS postId,
                p.attachment_id AS postAttachmentId,
                p.user_id AS userId,
                u.profile_photo_id AS profilePhotoId,
                p.title AS title,
                p.description AS description
            FROM posts p
                     JOIN users u ON p.user_id = u.id
                     JOIN attachment a ON a.id = p.attachment_id
            ORDER BY (SELECT COUNT(*) FROM follower f WHERE f.follower_id = u.id) DESC
                        
            limit :limit offset :offset
            """)
    List<PopularNewTrendyPostProjection> getTopPosts(@Param("offset") int offset, @Param("limit") int limit);

    List<Post> findAllByCategoryId(UUID categoryId);

    @Query(nativeQuery = true, value = """
            SELECT
                                         u.first_name ||' '|| u.last_name as fullname,
                                         TO_CHAR(p.created_at, 'DD.MM.YYYY ') AS date,
                                         p.id AS postId,
                                         p.attachment_id AS postAttachmentId,
                                         p.user_id AS userId,
                                         u.profile_photo_id AS profilePhotoId,
                                         p.title AS title,
                                         p.description AS description,
                                         p.views,
                                         coalesce ((select avg(r.mark_value) from rate r where r.post_id=p.id),0) as rate_value
                             FROM posts p
                                      JOIN users u ON p.user_id = u.id
                                      JOIN attachment a ON a.id = p.attachment_id
                             
                             where u.id=:userId
                             ORDER BY  date desc
                        
            """)
    List<UserPostProjection> getAllUserPostsByUserId(@Param(value = "userId") UUID userId);


    @Query(nativeQuery = true, value = """
        SELECT
            p.id AS postId,
            p.attachment_id AS postAttachmentId,
            p.user_id AS userId,
            u.profile_photo_id AS profilePhotoId,
            p.title AS title,
            p.description AS description
        FROM posts p
        JOIN users u ON p.user_id = u.id
        JOIN attachment a ON a.id = p.attachment_id
        JOIN category_tag ct ON ct.id = :tagsId
        JOIN posts_tags pt ON pt.post_id = p.id AND pt.tags_id = ct.id
        WHERE ct.id = :tagsId
        """)
    List<PopularNewTrendyPostProjection> getAllPostByTagsId(@Param("tagsId") UUID tagsId);




    @Query(nativeQuery = true, value = """

            select
                           p.id post_id,
                           p.attachment_id post_attachment_id
            ,
                           p.user_id,
                           u.profile_photo_id,
                           p.title,
                           p.description
           
                           from posts p
                           join users u on p.
            user_id=u.id
                           join
            attachment a on a.id=p.attachment_id
            where title ilike :search or description ilike :search
           
           
                        
                """)
    List<PopularNewTrendyPostProjection> searchPosts(String search);

    @Query(nativeQuery = true, value = """
        select
                 p.id post_id,
                 p.attachment_id post_attachment_id,
                 p.user_id,
                 u.profile_photo_id,
                 p.title,
                 p.description
                 from posts p
                 join users u on p.user_id = u.id
                 join attachment a on a.id = p.attachment_id
                 order by random()
                 limit :limit  offset :offset 

                 
""")
    List<PopularNewTrendyPostProjection> getAllPosts(@Param("offset") int offset, @Param("limit") int limit);



}