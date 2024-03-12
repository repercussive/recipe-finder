package com.repercussive.recipefinder.services.jpa;

import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.repositories.RecipeRepository;
import com.repercussive.recipefinder.services.RecipeService;
import org.springframework.stereotype.Service;

@Service
public class JpaRecipeService implements RecipeService {

    private final RecipeRepository recipeRepository;

    public JpaRecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Recipe createRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
