package com.repercussive.recipefinder.services;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    Recipe setRecipe(Recipe recipe);

    Optional<Recipe> findByName(String name);

    List<Recipe> findBestMatchingRecipes(List<Ingredient> ingredients, int pageNumber, int pageSize);
}
