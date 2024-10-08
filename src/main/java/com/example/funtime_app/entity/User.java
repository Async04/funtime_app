package com.example.funtime_app.entity;

import com.example.funtime_app.entity.enums.UserStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @OneToOne
    private Attachment profilePhoto;

    @CreationTimestamp
    private LocalDateTime signedAt;

    @CreationTimestamp
    private LocalDateTime lastUpdateAt;

    @Enumerated(value = EnumType.STRING)
    private UserStatus status = UserStatus.NOACTIVE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }
}