package com.repercussive.recipefinder.controllers;


import com.repercussive.recipefinder.IntegrationTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RecipeControllerIntegrationTest {
    private final IntegrationTestUtils testUtils;
    private final MockMvc mockMvc;

    @Autowired
    public RecipeControllerIntegrationTest(IntegrationTestUtils testUtils, MockMvc mockMvc) {
        this.testUtils = testUtils;
        this.mockMvc = mockMvc;
    }

    @Test
    public void testThatGetRecipeByIdReturnsHttpStatus200Ok() throws Exception {
        testUtils.populateDbWithSimpleData();

        mockMvc.perform(
                get("/recipes/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void testThatGetRecipeByIdReturnsRecipe() throws Exception {
        IntegrationTestUtils.SimpleRecipesData data = testUtils.populateDbWithSimpleData();

        mockMvc.perform(
                get("/recipes/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                jsonPath("$.id").value(1),
                jsonPath("$.name").value(data.fruitSalad().getName()),
                jsonPath("$.ingredientQuantities[0].ingredient.name")
                        .value(data.apple().getName()),
                jsonPath("$.ingredientQuantities[1].ingredient.name")
                        .value(data.banana().getName())
        );
    }

    @Test
    public void testThatGetSuggestedRecipesReturnsHttpStatus200Ok() throws Exception {
        testUtils.populateDbWithSimpleData();

        mockMvc.perform(
                get("/suggested_recipes")
                        .queryParam("ingredientIds", "1")
                        .queryParam("pageNumber", "1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void testThatGetSuggestedRecipesReturnsRecipesInExpectedOrder() throws Exception {
        IntegrationTestUtils.SimpleRecipesData data = testUtils.populateDbWithSimpleData();

        mockMvc.perform(
                get("/suggested_recipes")
                        .queryParam(
                                "ingredientIds",
                                data.apple().getId().toString(),
                                data.banana().getId().toString(),
                                data.carrot().getId().toString()
                        )
                        .queryParam("pageNumber", "1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                jsonPath("$[0].id").value(data.fruitSalad().getId()),
                jsonPath("$[1].id").value(data.vegetableMedley().getId())
        );
    }
}
