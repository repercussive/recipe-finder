export type Recipe = {
  id: number
  name: string,
  ingredientQuantities: [
    {
      ingredient: Ingredient,
      quantityPerPortion: number,
      quantityUnitName: string,
    }
  ]
}