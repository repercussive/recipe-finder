package com.repercussive.recipefinder.repositories;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.Recipe;

import java.util.List;

public interface RecipeRepositoryCustom {
    List<Recipe> findBestMatchingRecipes(List<Ingredient> ingredients, int pageNumber, int pageSize);
}
