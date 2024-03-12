package com.repercussive.recipefinder.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IngredientQuantityDto {
    private IngredientDto ingredient;
    private Double quantityPerPortion;
    private String quantityUnitName;
}
