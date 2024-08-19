package com.example.funtime_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "video")
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String title;
    private String description;
    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    @OneToMany
    private List<CategoryTag> tags;

    @OneToOne
    private Attachment attachment;

    private Integer views;

    @CreationTimestamp
    private LocalDateTime createdAt;

}