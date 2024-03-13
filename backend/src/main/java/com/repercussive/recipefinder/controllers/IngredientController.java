package com.repercussive.recipefinder.controllers;

import com.repercussive.recipefinder.dtos.IngredientDto;
import com.repercussive.recipefinder.helpers.StreamUtils;
import com.repercussive.recipefinder.mappers.IngredientMapper;
import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.services.IngredientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class IngredientController {

    private final IngredientService ingredientService;
    private final IngredientMapper ingredientMapper;

    public IngredientController(IngredientService ingredientService, IngredientMapper ingredientMapper) {
        this.ingredientService = ingredientService;
        this.ingredientMapper = ingredientMapper;
    }

    @GetMapping("/ingredients")
    public List<IngredientDto> getAllIngredients() {
        Iterable<Ingredient> ingredients = ingredientService.findAll();
        return StreamUtils.asStream(ingredients)
                .map(ingredientMapper::ingredientToIngredientDto)
                .toList();
    }
}
