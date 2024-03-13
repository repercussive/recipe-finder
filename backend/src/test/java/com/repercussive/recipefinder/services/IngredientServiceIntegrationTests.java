package com.repercussive.recipefinder.services;

import com.repercussive.recipefinder.IntegrationTestUtils;
import com.repercussive.recipefinder.models.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class IngredientServiceIntegrationTests {
    private final IntegrationTestUtils testUtils;
    private final IngredientService ingredientServiceUnderTest;

    @Autowired
    public IngredientServiceIntegrationTests(IntegrationTestUtils testUtils, IngredientService ingredientService) {
        this.testUtils = testUtils;
        this.ingredientServiceUnderTest = ingredientService;
    }

    @Test
    public void testThatCreatingIngredientNormalizesName() {
        testUtils.createTestIngredient("NoOdLeS");
        Optional<Ingredient> result = ingredientServiceUnderTest.findByName("noodles");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("noodles");
    }

    @Test
    public void testThatCreatingIngredientsAutoAssignsId() {
        testUtils.createTestIngredient("apple");
        testUtils.createTestIngredient("banana");
        testUtils.createTestIngredient("cherries");

        Optional<Ingredient> apple = ingredientServiceUnderTest.findByName("apple");
        Optional<Ingredient> banana = ingredientServiceUnderTest.findByName("banana");
        Optional<Ingredient> cherries = ingredientServiceUnderTest.findByName("cherries");

        assertThat(apple).isPresent();
        assertThat(apple.get().getId()).isEqualTo(1);

        assertThat(banana).isPresent();
        assertThat(banana.get().getId()).isEqualTo(2);

        assertThat(cherries).isPresent();
        assertThat(cherries.get().getId()).isEqualTo(3);
    }

    @Test
    public void testThatIngredientCannotBeCreatedWithNullName() {
        Ingredient ingredient = Ingredient.builder().name(null).build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> ingredientServiceUnderTest.setIngredient(ingredient)
        );
    }

    @Test
    public void testThatIngredientCannotBeCreatedWithEmptyName() {
        Ingredient ingredient = Ingredient.builder().name("").build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> ingredientServiceUnderTest.setIngredient(ingredient)
        );
    }

    @Test
    public void testThatDuplicateIngredientsCannotBeCreated() {
        testUtils.createTestIngredient("apple");
        Ingredient duplicateIngredient = Ingredient.builder().name("apple").build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> ingredientServiceUnderTest.setIngredient(duplicateIngredient)
        );
    }

    @Test
    public void testThatIngredientCanBeFoundByName() {
        testUtils.createTestIngredient("tomato");
        Optional<Ingredient> result = ingredientServiceUnderTest.findByName("tomato");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("tomato");
    }

    @Test
    public void testThatFindingAnIngredientIgnoresCase() {
        testUtils.createTestIngredient("peanuts");
        Optional<Ingredient> result = ingredientServiceUnderTest.findByName("PeAnUts");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("peanuts");
    }

    @Test
    public void testThatIngredientCanBeFoundById() {
        testUtils.createTestIngredient("lettuce");
        Optional<Ingredient> result = ingredientServiceUnderTest.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("lettuce");
        assertThat(result.get().getId()).isEqualTo(1);
    }

    @Test
    public void testThatFindingNonExistentIngredientReturnsEmptyOptional() {
        Optional<Ingredient> result = ingredientServiceUnderTest.findByName("nonexistent");
        assertThat(result).isNotPresent();
    }

    @Test
    public void testThatIngredientCanBeRenamed() {
        Ingredient ingredient = testUtils.createTestIngredient("onion");
        ingredient.setName("brown onion");
        ingredientServiceUnderTest.setIngredient(ingredient);

        Optional<Ingredient> result = ingredientServiceUnderTest.findByName("brown onion");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("brown onion");
        assertThat(result.get().getId()).isEqualTo(ingredient.getId());
    }

    @Test
    public void testThatIngredientCanBeDeleted() {
        Ingredient ingredient = testUtils.createTestIngredient("mushroom");
        ingredientServiceUnderTest.deleteIngredient(ingredient.getId());
        Optional<Ingredient> result = ingredientServiceUnderTest.findByName("mushroom");
        assertThat(result).isNotPresent();
    }
}
