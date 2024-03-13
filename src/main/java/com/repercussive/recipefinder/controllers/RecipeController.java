package com.repercussive.recipefinder.controllers;

import com.repercussive.recipefinder.dtos.RecipeDto;
import com.repercussive.recipefinder.mappers.IngredientMapper;
import com.repercussive.recipefinder.mappers.RecipeMapper;
import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeMapper recipeMapper;
    private final IngredientMapper ingredientMapper;

    public RecipeController(RecipeService recipeService, RecipeMapper recipeMapper, IngredientMapper ingredientMapper) {
        this.recipeService = recipeService;
        this.recipeMapper = recipeMapper;
        this.ingredientMapper = ingredientMapper;
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<RecipeDto> getRecipeById(@PathVariable("id") String idString) {
        try {
            Long id = Long.parseLong(idString);
            Optional<Recipe> recipe = recipeService.findById(id);
            return recipe.map(recipeEntity -> {
                RecipeDto recipeDto = recipeMapper.recipeToRecipeDto(recipeEntity);
                return new ResponseEntity<>(recipeDto, HttpStatus.OK);
            }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (NumberFormatException nfe) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid recipe id");
        }
    }

    @GetMapping("/suggested_recipes")
    public List<RecipeDto> getSuggestedRecipes(
            @RequestParam List<Long> ingredientIds,
            @RequestParam Integer pageNumber
    ) {
        List<Ingredient> ingredients = ingredientIds.stream()
                .map(id -> Ingredient.builder().id(id).build())
                .toList();
        List<Recipe> recipes = recipeService.findBestMatchingRecipes(ingredients, pageNumber, 10);
        return recipes.stream().map(recipeMapper::recipeToRecipeDto).toList();
    }
}
