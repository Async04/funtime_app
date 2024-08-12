package com.example.funtime_app.entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class CategoryTag {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String tagName;
    @ManyToOne
    private Category category;

}
