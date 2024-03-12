package com.repercussive.recipefinder;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.IngredientQuantity;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.services.IngredientService;
import com.repercussive.recipefinder.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IntegrationTestUtils {
    private final IngredientService ingredientService;
    private final RecipeService recipeService;

    @Autowired
    public IntegrationTestUtils(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    public Ingredient createTestIngredient(String ingredientName) {
        Ingredient ingredient = Ingredient.builder().name(ingredientName).build();
        return ingredientService.setIngredient(ingredient);
    }

    public Recipe createTestRecipe(String recipeName, List<Ingredient> ingredients, List<IngredientQuantity> ingredientQuantities) {
        Recipe recipe = Recipe.builder()
                .name(recipeName)
                .ingredients(ingredients)
                .build();

        for (IngredientQuantity ingredientQuantity : ingredientQuantities) {
            ingredientQuantity.setRecipe(recipe);
            recipe.getIngredientQuantities().add(ingredientQuantity);
        }

        return recipeService.setRecipe(recipe);
    }
}
