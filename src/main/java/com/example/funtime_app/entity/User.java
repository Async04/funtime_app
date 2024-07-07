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
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

    @ManyToMany
    private List<Role> roles;

    @OneToOne
    private Attachment profilePhoto;

    @CreationTimestamp
    private LocalDateTime signedAt;

    @CreationTimestamp
    private LocalDateTime lastUpdateAt;

}