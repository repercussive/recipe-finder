package com.repercussive.recipefinder.mappers;

import com.repercussive.recipefinder.dtos.RecipeDto;
import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.IngredientQuantity;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.services.IngredientService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.MessageFormat;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class RecipeMapper {
    @Autowired
    private IngredientService ingredientService;

    public Recipe recipeDtoToRecipe(RecipeDto recipeDto) {
        Recipe recipe = Recipe.builder()
                .id(recipeDto.getId())
                .name(recipeDto.getName())
                .build();

        for (var ingredientQuantityDto : recipeDto.getIngredientQuantities()) {
            Optional<Long> ingredientId = Optional.ofNullable(ingredientQuantityDto.getIngredient().getId());
            Optional<String> ingredientName = Optional.ofNullable(ingredientQuantityDto.getIngredient().getName());

            Optional<Ingredient> ingredient = ingredientId
                    .map(ingredientService::findById)
                    .orElseGet(() -> ingredientService.findByName(
                            ingredientName.orElseThrow(() -> new IllegalArgumentException("No ingredient id or name provided"))
                    ));

            if (ingredient.isEmpty()) {
                throw new IllegalArgumentException(
                        MessageFormat.format("Ingredient does not exist (id: {0}, name: {1})",
                                ingredientId.orElse(-1L),
                                ingredientName.orElse("<none provided>")
                        )
                );
            }

            recipe.getIngredients().add(ingredient.get());

            IngredientQuantity ingredientQuantity = IngredientQuantity.builder()
                    .recipe(recipe)
                    .ingredient(ingredient.get())
                    .quantityPerPortion(ingredientQuantityDto.getQuantityPerPortion())
                    .quantityUnitName(ingredientQuantityDto.getQuantityUnitName())
                    .build();

            recipe.getIngredientQuantities().add(ingredientQuantity);
        }

        return recipe;
    }
}
