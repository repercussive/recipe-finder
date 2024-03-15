import { useIngredients } from '@context/IngredientsContext'
import { Ingredient } from '@src/types/Ingredient'
import { useMemo, useState } from 'react'
import Select, { MultiValue, ThemeConfig } from 'react-select'

type SelectOption = { value: number, label: string }

function IngredientsInput() {
  const { ingredients, loadIngredients } = useIngredients()
  const [selectedIngredients, setSelectedIngredients] = useState<MultiValue<SelectOption>>([])
  const options = useMemo(() => mapIngredientsToOptions(ingredients), [ingredients])

  const handleChange = (newValue: MultiValue<SelectOption>) => {
    setSelectedIngredients(newValue)
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
        placeholder={"Search for ingredients"}
        noOptionsMessage={() => ingredients.length > 0 ? "No matches found" : "Loading ingredients..."}
        theme={themeConfig}
      />
      <p>We'll suggest recipe ideas for you.</p>
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