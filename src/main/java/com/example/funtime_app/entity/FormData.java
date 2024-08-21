package com.example.funtime_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
public class FormData {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    private String subject;
    private String name;
    private String email;
    private String explanation;
    @OneToOne
    private Attachment attachment;


}
