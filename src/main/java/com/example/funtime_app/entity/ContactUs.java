package com.example.funtime_app.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "contact_us")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContactUs {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String subject;
    private String name;
    private String email;

    private String description;

    @OneToOne
    private Attachment file;

    @CreationTimestamp
    private LocalDateTime createdAt;


}