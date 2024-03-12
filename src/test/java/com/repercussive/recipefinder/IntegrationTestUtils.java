package com.repercussive.recipefinder;

import com.repercussive.recipefinder.helpers.JsonToEntityConverter;
import com.repercussive.recipefinder.mappers.IngredientMapper;
import com.repercussive.recipefinder.mappers.RecipeMapper;
import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.IngredientQuantity;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.services.IngredientService;
import com.repercussive.recipefinder.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class IntegrationTestUtils {
    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final JsonToEntityConverter jsonToEntityConverter;
    private final IngredientMapper ingredientMapper;
    private final RecipeMapper recipeMapper;

    @Autowired
    public IntegrationTestUtils(
            IngredientService ingredientService,
            RecipeService recipeService,
            JsonToEntityConverter jsonToEntityConverter,
            IngredientMapper ingredientMapper,
            RecipeMapper recipeMapper
    ) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.jsonToEntityConverter = jsonToEntityConverter;
        this.ingredientMapper = ingredientMapper;
        this.recipeMapper = recipeMapper;
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

    public Recipe createTestRecipe(String recipeName, List<Ingredient> ingredients) {
        return createTestRecipe(recipeName, ingredients, List.of());
    }

    public Recipe createTestRecipe(String recipeName) {
        return createTestRecipe(recipeName, List.of(), List.of());
    }

    public SimpleRecipesData populateDbWithSimpleData() {
        Ingredient apple = createTestIngredient("apple");
        Ingredient banana = createTestIngredient("banana");
        Ingredient carrot = createTestIngredient("carrot");
        Ingredient tomato = createTestIngredient("tomato");
        Recipe fruitSalad = createTestRecipe("Fruit Salad", List.of(apple, banana));
        Recipe vegetableMedley = createTestRecipe("Vegetable Medley", List.of(carrot, tomato));
        return new SimpleRecipesData(apple, banana, carrot, tomato, fruitSalad, vegetableMedley);
    }

    public record SimpleRecipesData(Ingredient apple, Ingredient banana, Ingredient carrot, Ingredient tomato, Recipe fruitSalad, Recipe vegetableMedley) {
    }
}
