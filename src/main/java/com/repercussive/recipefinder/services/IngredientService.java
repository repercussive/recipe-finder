package com.repercussive.recipefinder.services;

import com.repercussive.recipefinder.models.Ingredient;
import org.springframework.cache.annotation.Cacheable;

import java.util.Optional;

public interface IngredientService {
    Ingredient createIngredient(Ingredient ingredient);

    @Cacheable("ingredients")
    Optional<Ingredient> findById(Long id);

    @Cacheable("ingredients")

    Optional<Ingredient> findByName(String name);
}
