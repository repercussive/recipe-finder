import React, { createContext, useState, useCallback, useContext } from 'react'
import { Ingredient } from '@customTypes/Ingredient'
import { BACKEND_API_BASE_URL } from '@config/constants'
import { fetchJson } from '@src/helpers/fetchJson'

interface IngredientsContextValue {
  loadIngredients: () => void
  ingredients: Ingredient[]
}

const IngredientsContext = createContext<IngredientsContextValue>(null!)

export const useIngredients = () => useContext(IngredientsContext)

export const IngredientsProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  const [ingredients, setIngredients] = useState<Ingredient[]>([])

  const loadIngredients = useCallback(async () => {
    if (ingredients.length > 0) return
    const url = `${BACKEND_API_BASE_URL}/ingredients`
    const responseData = await fetchJson<Ingredient[]>(url)
    setIngredients(responseData)
  }, [ingredients])

  return (
    <IngredientsContext.Provider value={{ ingredients, loadIngredients }}>
      {children}
    </IngredientsContext.Provider>
  )
}