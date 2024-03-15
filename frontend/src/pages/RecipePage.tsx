import { BACKEND_API_BASE_URL } from '@src/config/constants'
import { useRecipeSearch } from '@src/context/RecipeSearchContext'
import { fetchJson } from '@src/helpers/fetchJson'
import { Recipe } from '@src/types/Recipe'
import { useEffect, useState } from 'react'
import { useRoute } from 'wouter'

const RecipePage: React.FC = () => {
  const { recipeResults } = useRecipeSearch()
  const [_, params] = useRoute('/recipes/:id')
  const [recipe, setRecipe] = useState<null | Recipe>(null)
  const [error, setError] = useState<null | string>(null)

  useEffect(() => {
    const recipeId = params?.id

    if (recipeId !== '') {
      const recipeIdNumber = parseInt(recipeId!)

      if (Number.isNaN(recipeIdNumber)) {
        return setError('Invalid recipe ID')
      }

      const cachedRecipe = recipeResults?.find((r) => r.id === recipeIdNumber)
      if (cachedRecipe) {
        return setRecipe(cachedRecipe)
      }

      fetchJson<Recipe>(`${BACKEND_API_BASE_URL}/recipes/${recipeId}`)
        .then((data) => setRecipe(data))
        .catch(() => setError('Error loading recipe.'))
    }
  }, [])

  if (error !== null) return <p>{error}</p>
  if (recipe === null) return <p>Loading...</p>

  return (
    <>
      <h2>{recipe.name}</h2>
      <h3>Ingredients</h3>
      <ul>
        {recipe.ingredientQuantities.map((ingredientQuantity) => (
          <li key={ingredientQuantity.ingredient.id}>
            {ingredientQuantity.ingredient.name}
          </li>
        ))}
      </ul>
    </>
  )
}

export default RecipePage