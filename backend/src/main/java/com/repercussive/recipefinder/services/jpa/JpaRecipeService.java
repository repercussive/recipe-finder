package com.repercussive.recipefinder.services.jpa;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.repositories.RecipeRepository;
import com.repercussive.recipefinder.services.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JpaRecipeService implements RecipeService {

    private final RecipeRepository recipeRepository;

    public JpaRecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Optional<Recipe> findById(Long id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Optional<Recipe> findByName(String name) {
        return recipeRepository.findByNameIgnoreCase(name);
    }

    @Override
    public List<Recipe> findBestMatchingRecipes(List<Ingredient> ingredients, int pageNumber, int pageSize) {
        return recipeRepository.findBestMatchingRecipes(ingredients, pageNumber, pageSize);
    }

    @Override
    public Recipe setRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
