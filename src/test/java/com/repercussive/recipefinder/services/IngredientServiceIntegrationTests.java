package com.repercussive.recipefinder.services;

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

    private final IngredientService testIngredientService;

    @Autowired
    public IngredientServiceIntegrationTests(IngredientService testIngredientService) {
        this.testIngredientService = testIngredientService;
    }

    private Ingredient createTestIngredient(String ingredientName) {
        Ingredient ingredient = Ingredient.builder().name(ingredientName).build();
        return testIngredientService.setIngredient(ingredient);
    }

    @Test
    public void testThatCreatingIngredientNormalizesName() {
        createTestIngredient("NoOdLeS");
        Optional<Ingredient> result = testIngredientService.findByName("noodles");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("noodles");
    }

    @Test
    public void testThatCreatingIngredientsAutoAssignsId() {
        createTestIngredient("apple");
        createTestIngredient("banana");
        createTestIngredient("cherries");

        Optional<Ingredient> apple = testIngredientService.findByName("apple");
        Optional<Ingredient> banana = testIngredientService.findByName("banana");
        Optional<Ingredient> cherries = testIngredientService.findByName("cherries");

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
                () -> testIngredientService.setIngredient(ingredient)
        );
    }

    @Test
    public void testThatIngredientCannotBeCreatedWithEmptyName() {
        Ingredient ingredient = Ingredient.builder().name("").build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> testIngredientService.setIngredient(ingredient)
        );
    }

    @Test
    public void testThatDuplicateIngredientsCannotBeCreated() {
        createTestIngredient("apple");
        Ingredient duplicateIngredient = Ingredient.builder().name("apple").build();
        assertThrows(
                DataIntegrityViolationException.class,
                () -> testIngredientService.setIngredient(duplicateIngredient)
        );
    }

    @Test
    public void testThatIngredientCanBeFoundByName() {
        createTestIngredient("tomato");
        Optional<Ingredient> result = testIngredientService.findByName("tomato");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("tomato");
    }

    @Test
    public void testThatFindingAnIngredientIgnoresCase() {
        createTestIngredient("peanuts");
        Optional<Ingredient> result = testIngredientService.findByName("PeAnUts");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("peanuts");
    }

    @Test
    public void testThatIngredientCanBeFoundById() {
        createTestIngredient("lettuce");
        Optional<Ingredient> result = testIngredientService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("lettuce");
        assertThat(result.get().getId()).isEqualTo(1);
    }

    @Test
    public void testThatFindingNonExistentIngredientReturnsEmptyOptional() {
        Optional<Ingredient> result = testIngredientService.findByName("nonexistent");
        assertThat(result).isNotPresent();
    }

    @Test
    public void testThatIngredientCanBeUpdated() {
        Ingredient ingredient = createTestIngredient("onion");
        ingredient.setName("brown onion");
        testIngredientService.setIngredient(ingredient);

        Optional<Ingredient> result = testIngredientService.findByName("brown onion");
        assertThat(result).isPresent();
        assertThat(result.get().getName()).isEqualTo("brown onion");
        assertThat(result.get().getId()).isEqualTo(ingredient.getId());
    }

    @Test
    public void testThatIngredientCanBeDeleted() {
        Ingredient ingredient = createTestIngredient("mushroom");
        testIngredientService.deleteIngredient(ingredient.getId());
        Optional<Ingredient> result = testIngredientService.findByName("mushroom");
        assertThat(result).isNotPresent();
    }
}
