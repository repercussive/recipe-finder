import { useIngredients } from '@context/IngredientsContext'
import Spacer from '@src/components/Spacer'
import { useRecipeSearch } from '@src/context/RecipeSearchContext'
import { Ingredient } from '@src/types/Ingredient'
import { useMemo, useState } from 'react'
import Select, { MultiValue, ThemeConfig } from 'react-select'

type SelectOption = { value: number, label: string }

const IngredientsInput: React.FC = () => {
  const { ingredients, loadIngredients } = useIngredients()
  const { findSuggestedRecipes } = useRecipeSearch()
  const [selectedIngredients, setSelectedIngredients] = useState<MultiValue<SelectOption>>([])
  const options = useMemo(() => mapIngredientsToOptions(ingredients), [ingredients])

  const handleChange = (newValue: MultiValue<SelectOption>) => {
    setSelectedIngredients(newValue)
  }

  const handleFindRecipes = () => {
    const ingredientIds = selectedIngredients.map((ingredient) => ingredient.value)
    findSuggestedRecipes(ingredientIds, 1)
  }

  return (
    <>
      <h3>Enter ingredients you've got at home</h3>
      <Select
        options={options}
        isMulti={true}
        value={selectedIngredients}
        onChange={handleChange}
        onMenuOpen={loadIngredients}
        placeholder="Search for ingredients"
        noOptionsMessage={() => ingredients.length > 0 ? "No matches found" : "Loading ingredients..."}
        theme={themeConfig}
      />
      <Spacer mb="0.5rem" />
      <button
        onClick={handleFindRecipes}
        disabled={selectedIngredients.length === 0}
      >
        Find recipes
      </button>
    </>
  )
}

function mapIngredientsToOptions(ingredients: Ingredient[]): MultiValue<SelectOption> {
  return ingredients
    .sort((a, b) => a.name > b.name ? 1 : -1)
    .map((ingredient) => ({
      value: ingredient.id,
      label: ingredient.name
    }))
}

const themeConfig: ThemeConfig = (theme) => ({
  ...theme,
  colors: {
    ...theme.colors,
    neutral0: 'var(--color-bg)',
    primary: 'var(--color-text)',
    primary25: 'var(--color-bg-secondary)',
    primary50: 'var(--color-bg-secondary)',
    neutral10: 'var(--color-bg-secondary)',
    neutral80: 'var(--color-text)',
  }
})

export default IngredientsInput