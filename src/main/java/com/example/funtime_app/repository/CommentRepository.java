package com.example.funtime_app.repository;

import com.example.funtime_app.dto.response.CommentResponseProjection;
import com.example.funtime_app.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findAllByPostId(UUID postId);


    List<Comment> findAllByParentCommentId(UUID parentCommentId);

    @Query(nativeQuery = true, value = """

            select
          
              c.id as id,
              a.id user_attachment_id,
              u.first_name || ' ' || u.last_name as full_name,
              c.created_at as created_at,
              c.body,
              (select count(*) from comment cm where c.id=cm.parent_comment_id) reply_count
          
              from comment c
                  join users u on u.id=c.commented_by_id
                  join posts p on p.id=c.post_id
                  join attachment a on a.id=u.profile_photo_id
            where c.post_id=:postId
            
            """)
    List<CommentResponseProjection> getAllPostComments(UUID postId);

    @Query(nativeQuery = true, value = """
            
            select
            
                c.id as id,
                a.id user_attachment_id,
                u.first_name || ' ' || u.last_name as full_name,
                c.created_at as created_at,
                c.body,
                (select count(*) from comment cm where c.id=cm.parent_comment_id) reply_count
            
                from comment c
                    join users u on u.id=c.commented_by_id
                    join attachment a on a.id=u.profile_photo_id
            
            
            where c.parent_comment_id=:commentId
            
            """)
    List<CommentResponseProjection> getAllReplyComment(@Param("commentId") UUID commentId);
}