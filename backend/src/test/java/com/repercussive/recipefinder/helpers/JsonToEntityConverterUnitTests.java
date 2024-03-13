package com.repercussive.recipefinder.helpers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.repercussive.recipefinder.mappers.IngredientMapper;
import com.repercussive.recipefinder.models.Ingredient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JsonToEntityConverterUnitTests {
    JsonToEntityConverter jsonToEntityConverter;
    IngredientMapper ingredientMapper;

    @Autowired
    public JsonToEntityConverterUnitTests(
            JsonToEntityConverter jsonToEntityConverter,
            IngredientMapper ingredientMapper
    ) {
        this.jsonToEntityConverter = jsonToEntityConverter;
        this.ingredientMapper = ingredientMapper;
    }

    @Test
    public void testThatSingleIngredientJsonIsConvertedToIngredientEntity() throws IOException {
        String json = "{\"name\":\"apple\"}";
        Ingredient ingredient = jsonToEntityConverter.jsonToEntitySingle(
                json,
                ingredientMapper::ingredientDtoToIngredient,
                new TypeReference<>() {
                }
        );
        assertThat(ingredient.getName()).isEqualTo("apple");
    }

    @Test
    public void testThatListOfIngredientsJsonIsConvertedToListOfIngredientEntities() throws IOException {
        String json = "[{\"name\":\"apple\"},{\"name\":\"banana\"}]";
        List<Ingredient> ingredients = jsonToEntityConverter.jsonToEntityList(
                json,
                ingredientMapper::ingredientDtoToIngredient,
                new TypeReference<>() {
                }
        );
        assertThat(ingredients).hasSize(2);
        assertThat(ingredients.get(0).getName()).isEqualTo("apple");
        assertThat(ingredients.get(1).getName()).isEqualTo("banana");
    }
}
