package com.repercussive.recipefinder.mappers;

import com.repercussive.recipefinder.dtos.IngredientDto;
import com.repercussive.recipefinder.models.Ingredient;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class IngredientMapper {
    public abstract IngredientDto ingredientToIngredientDto(Ingredient ingredient);
    public abstract Ingredient ingredientDtoToIngredient(IngredientDto ingredientDto);
}
