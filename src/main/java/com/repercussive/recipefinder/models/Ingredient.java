package com.repercussive.recipefinder.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "ingredient")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredient_id_seq")
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @PrePersist
    @PreUpdate
    private void normalizeName() {
        if (name == null || name.isEmpty()) {
            throw new DataIntegrityViolationException("Ingredient name cannot be null or empty");
        }
        name = name.toLowerCase();
    }
}
