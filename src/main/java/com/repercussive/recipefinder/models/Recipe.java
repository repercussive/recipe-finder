package com.repercussive.recipefinder.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(exclude = { "ingredients", "ingredientQuantities" })
@ToString(exclude = { "ingredients", "ingredientQuantities" })
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "recipe")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "recipe_id_seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
        name = "recipe_ingredient",
        joinColumns = @JoinColumn(name = "recipe.id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient.id")
    )
    @Builder.Default
    private List<Ingredient> ingredients = new ArrayList<>();

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<IngredientQuantity> ingredientQuantities = new ArrayList<>();

    @PrePersist
    @PreUpdate
    private void validateName() {
        if (name == null || name.isEmpty()) {
            throw new DataIntegrityViolationException("Recipe name cannot be null or empty");
        }
    }
}
