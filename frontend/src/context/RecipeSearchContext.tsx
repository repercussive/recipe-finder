import React, { createContext, useState, useCallback, useContext } from 'react'
import { BACKEND_API_BASE_URL } from '@config/constants'
import { Recipe } from '@src/types/Recipe'
import { fetchJson } from '@src/helpers/fetchJson'

interface RecipeSearchContextValue {
  findSuggestedRecipes: (ingredientIds: number[], pageNumber: number) => void
  recipeResults: null | Recipe[]
}

const RecipeSearchContext = createContext<RecipeSearchContextValue>(null!)

export const useRecipeSearch = () => useContext(RecipeSearchContext)

export const RecipeSearchProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [recipeResults, setRecipeResults] = useState<null | Recipe[]>(null)

  const findSuggestedRecipes = useCallback(async (ingredientIds: number[], pageNumber: number) => {
    let url = `${BACKEND_API_BASE_URL}/suggested_recipes?ingredientIds`
    if (ingredientIds.length > 0) {
      url += `=${ingredientIds.join(',')}`
    }
    url += `&pageNumber=${pageNumber}`
    const responseData = await fetchJson<Recipe[]>(url)
    setRecipeResults(responseData)
  }, [recipeResults])

  return (
    <RecipeSearchContext.Provider value={{ findSuggestedRecipes, recipeResults }}>
      {children}
    </RecipeSearchContext.Provider>
  )
}