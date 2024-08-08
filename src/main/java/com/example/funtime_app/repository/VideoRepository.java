package com.example.funtime_app.repository;

import com.example.funtime_app.entity.Video;
import com.example.funtime_app.projection.LatestVideoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    @Query(nativeQuery = true, value = """


            select v.id video_id, a.id video_attachment_id, v.title, v.description from video v
                    join
            attachment a on a.id=v.attachment_id
                    join users u on u.id = v.user_id
            order by v.created_at desc limit 10
                """)
    List<LatestVideoProjection> getLatestVideo();

}