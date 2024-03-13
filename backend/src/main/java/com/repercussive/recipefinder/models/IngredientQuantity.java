package com.repercussive.recipefinder.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name= "recipe_ingredient_quantity")
public class IngredientQuantity {

    @Id
    @ManyToOne
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @Id
    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @Column(name = "quantity_per_portion")
    private Double quantityPerPortion;

    @Column(name = "quantity_unit_name")
    private String quantityUnitName;
}