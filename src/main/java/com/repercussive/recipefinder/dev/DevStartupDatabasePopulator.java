package com.repercussive.recipefinder.dev;

import com.repercussive.recipefinder.helpers.JsonToEntityConverter;
import com.repercussive.recipefinder.mappers.IngredientMapper;
import com.repercussive.recipefinder.mappers.RecipeMapper;
import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.repositories.IngredientRepository;
import com.repercussive.recipefinder.repositories.RecipeRepository;
import com.repercussive.recipefinder.services.IngredientService;
import com.repercussive.recipefinder.services.RecipeService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Profile("dev")
public class DevStartupDatabasePopulator {
    private final JsonToEntityConverter jsonToEntityConverter;

    private final IngredientService ingredientService;
    private final RecipeService recipeService;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;
    private final RecipeMapper recipeMapper;

    public DevStartupDatabasePopulator(
            IngredientService ingredientService,
            RecipeService recipeService,
            IngredientRepository ingredientRepository,
            RecipeRepository recipeRepository,
            IngredientMapper ingredientMapper,
            RecipeMapper recipeMapper,
            JsonToEntityConverter jsonToEntityConverter
    ) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientMapper = ingredientMapper;
        this.recipeMapper = recipeMapper;
        this.jsonToEntityConverter = jsonToEntityConverter;
    }

    @PostConstruct
    public void runOnStartup() {
        clearDatabase();
        try {
            populateIngredients();
            populateRecipes();
        } catch (IOException e) {
            System.out.println("Error reading data json file: " + e.getMessage());
        }
    }

    private void clearDatabase() {
        System.out.println("[Clearing database]");
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    private void populateIngredients() throws IOException {
        System.out.println("[Populating ingredients]");
        List<Ingredient> ingredients = jsonToEntityConverter.jsonToEntityList(
                readFromFile("/sample-data/ingredients.json"),
                ingredientMapper::ingredientDtoToIngredient,
                new TypeReference<>() {
                }
        );
        ingredients.forEach(ingredientService::setIngredient);
    }

    private void populateRecipes() throws IOException {
        System.out.println("[Populating recipes]");
        List<Recipe> ingredients = jsonToEntityConverter.jsonToEntityList(
                readFromFile("/sample-data/recipes.json"),
                recipeMapper::recipeDtoToRecipe,
                new TypeReference<>() {
                }
        );
        ingredients.forEach(recipeService::setRecipe);
    }

    public InputStream readFromFile(String dataFilePath) {
        return TypeReference.class.getResourceAsStream(dataFilePath);
    }
}