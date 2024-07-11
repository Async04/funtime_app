package com.example.funtime_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String title;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne
    private Category category;

    @OneToOne
    private Attachment attachment;

    private Integer views;

    @CreationTimestamp
    private LocalDateTime createdAt;


}