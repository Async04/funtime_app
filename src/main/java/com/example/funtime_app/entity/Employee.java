package com.example.funtime_app.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "employee")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String firstName;
    private String lastName;

    private String job;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Attachment attachment;

}