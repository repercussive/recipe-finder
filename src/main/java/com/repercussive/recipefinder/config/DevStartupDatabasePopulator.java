package com.repercussive.recipefinder.config;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.repositories.IngredientRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
@Profile("dev")
public class DevStartupDatabasePopulator {
    private ObjectMapper jsonMapper;

    private final IngredientRepository ingredientRepository;

    public DevStartupDatabasePopulator(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @PostConstruct
    public void runOnStartup() {
        jsonMapper = new ObjectMapper();

        try {
            populateIngredients();
        } catch (IOException e) {
            System.out.println("Error reading data json file: " + e.getMessage());
        }
    }

    private void populateIngredients() throws IOException {
        System.out.println("[Populating ingredients table]");
        ingredientRepository.deleteAll();
        TypeReference<List<Ingredient>> typeReference = new TypeReference<>(){};
        try (InputStream inputStream = TypeReference.class.getResourceAsStream("/sample-data/ingredients.json")) {
            List<Ingredient> ingredients = jsonMapper.readValue(inputStream, typeReference);
            for (Ingredient ingredient : ingredients) {
                ingredientRepository.save(ingredient);
            }
        }
    }
}