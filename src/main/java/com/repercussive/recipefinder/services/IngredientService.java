package com.repercussive.recipefinder.services;

import com.repercussive.recipefinder.models.Ingredient;
import java.util.Optional;

public interface IngredientService {
    Optional<Ingredient> findById(Long id);

    Optional<Ingredient> findByName(String name);
}
