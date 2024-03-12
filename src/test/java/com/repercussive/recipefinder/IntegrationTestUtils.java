package com.repercussive.recipefinder;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.services.IngredientService;
import org.springframework.stereotype.Component;

@Component
public class IntegrationTestUtils {
    private static IngredientService ingredientService;

    public IntegrationTestUtils(IngredientService ingredientService) {
        IntegrationTestUtils.ingredientService = ingredientService;
    }

    public Ingredient createTestIngredient(String ingredientName) {
        Ingredient ingredient = Ingredient.builder().name(ingredientName).build();
        return ingredientService.setIngredient(ingredient);
    }
}
