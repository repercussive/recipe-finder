package com.repercussive.recipefinder.services;

import com.repercussive.recipefinder.IntegrationTestUtils;
import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.IngredientQuantity;
import com.repercussive.recipefinder.models.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RecipeServiceIntegrationTests {

    private final IntegrationTestUtils testUtils;
    private final RecipeService recipeServiceUnderTest;

    @Autowired
    public RecipeServiceIntegrationTests(IntegrationTestUtils testUtils, RecipeService recipeService) {
        this.testUtils = testUtils;
        this.recipeServiceUnderTest = recipeService;
    }

    @Test
    @Transactional
    public void testThatRecipeCanBeCreatedAndFound() {
        Ingredient ingredient1 = testUtils.createTestIngredient("noodles");
        Ingredient ingredient2 = testUtils.createTestIngredient("fish sauce");

        testUtils.createTestRecipe(
                "Pad Thai",
                List.of(ingredient1, ingredient2),
                List.of(
                        IngredientQuantity.builder()
                                .ingredient(ingredient1)
                                .quantityUnitName("g")
                                .quantityPerPortion(75d)
                                .build(),
                        IngredientQuantity.builder()
                                .ingredient(ingredient2)
                                .quantityUnitName("tbsp")
                                .quantityPerPortion(0.5)
                                .build()
                )
        );

        Optional<Recipe> result = recipeServiceUnderTest.findByName("Pad Thai");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Pad Thai");
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getIngredients()).containsExactly(ingredient1, ingredient2);
    }

    @Test
    public void testThatRecipeCannotBeCreatedWithNullName() {
        Recipe recipe = Recipe.builder().name(null).build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> recipeServiceUnderTest.setRecipe(recipe)
        );
    }

    @Test
    public void testThatRecipeCannotBeCreatedWithEmptyName() {
        Recipe recipe = Recipe.builder().name("").build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> recipeServiceUnderTest.setRecipe(recipe)
        );
    }

    @Test
    public void testThatDuplicateRecipesCannotBeCreated() {
        testUtils.createTestRecipe("Pad Thai", List.of(), List.of());
        Recipe duplicateRecipe = Recipe.builder().name("Pad Thai").build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> recipeServiceUnderTest.setRecipe(duplicateRecipe)
        );
    }

    @Test
    public void testThatRecipeCanBeRenamed() {
        Recipe recipe = testUtils.createTestRecipe("Pad Thai", List.of(), List.of());
        recipe.setName("Delicious Pad Thai");
        recipeServiceUnderTest.setRecipe(recipe);

        Optional<Recipe> result = recipeServiceUnderTest.findByName("Delicious Pad Thai");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("Delicious Pad Thai");
    }
}
