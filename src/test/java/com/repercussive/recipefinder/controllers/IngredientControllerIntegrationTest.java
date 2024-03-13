package com.repercussive.recipefinder.controllers;

import com.repercussive.recipefinder.IntegrationTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class IngredientControllerIntegrationTest {
    private final IntegrationTestUtils testUtils;
    private final MockMvc mockMvc;

    @Autowired
    public IngredientControllerIntegrationTest(IntegrationTestUtils testUtils, MockMvc mockMvc) {
        this.testUtils = testUtils;
        this.mockMvc = mockMvc;
    }

    @Test
    public void testThatGetAllIngredientsReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                get("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                status().isOk()
        );
    }

    @Test
    public void testThatGetAllIngredientsReturnsAllIngredients() throws Exception {
        IntegrationTestUtils.SimpleRecipesData data = testUtils.populateDbWithSimpleData();

        mockMvc.perform(
                get("/ingredients").contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                jsonPath("$", hasSize(4)),
                jsonPath("$[0].name").value(data.apple().getName()),
                jsonPath("$[0].id").value(data.apple().getId()),
                jsonPath("$[1].name").value(data.banana().getName()),
                jsonPath("$[1].id").value(data.banana().getId()),
                jsonPath("$[2].name").value(data.carrot().getName()),
                jsonPath("$[2].id").value(data.carrot().getId()),
                jsonPath("$[3].name").value(data.tomato().getName()),
                jsonPath("$[3].id").value(data.tomato().getId())
        );
    }
}
