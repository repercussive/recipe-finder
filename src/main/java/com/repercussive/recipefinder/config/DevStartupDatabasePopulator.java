package com.repercussive.recipefinder.config;

import com.repercussive.recipefinder.mappers.IngredientMapper;
import com.repercussive.recipefinder.mappers.RecipeMapper;
import com.repercussive.recipefinder.repositories.IngredientRepository;
import com.repercussive.recipefinder.repositories.RecipeRepository;
import com.repercussive.recipefinder.services.IngredientService;
import com.repercussive.recipefinder.services.RecipeService;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Component
@Profile("dev")
public class DevStartupDatabasePopulator {
    private ObjectMapper jsonMapper;

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
            RecipeMapper recipeMapper
    ) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientMapper = ingredientMapper;
        this.recipeMapper = recipeMapper;
    }

    @PostConstruct
    public void runOnStartup() {
        jsonMapper = new ObjectMapper();
        clearDatabase();

        try {
            populateDbFromJsonFile(
                    "/sample-data/ingredients.json",
                    ingredientMapper::ingredientDtoToIngredient,
                    ingredientService::createIngredient,
                    new TypeReference<>() {
                    }
            );
            populateDbFromJsonFile(
                    "/sample-data/recipes.json",
                    recipeMapper::recipeDtoToRecipe,
                    recipeService::createRecipe,
                    new TypeReference<>() {
                    }
            );
        } catch (IOException e) {
            System.out.println("Error reading data json file: " + e.getMessage());
        }
    }

    private void clearDatabase() {
        System.out.println("[Clearing database]");
        recipeRepository.deleteAll();
        ingredientRepository.deleteAll();
    }

    private <TDto, TEntity> void populateDbFromJsonFile(
            String dataFilePath,
            Function<TDto, TEntity> dtoToEntityMapper,
            Consumer<TEntity> createEntityFunction,
            TypeReference<List<TDto>> dtoListTypeRef
    ) throws IOException {

        try (InputStream inputStream = TypeReference.class.getResourceAsStream(dataFilePath)) {
            List<TDto> dtos = jsonMapper.readValue(inputStream, dtoListTypeRef);
            for (TDto dto : dtos) {
                try {
                    TEntity entity = dtoToEntityMapper.apply(dto);
                    createEntityFunction.accept(entity);
                }
                catch (Exception e) {
                    System.out.println(dto.getClass());
                }
            }
        }
    }
}