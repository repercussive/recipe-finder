import React, { createContext, useState, useEffect, useCallback, useContext } from 'react'
import { Ingredient } from '../types/Ingredient'
import { BACKEND_API_BASE_URL } from '../components/config/constants'

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
    try {  
      const response = await fetch(`${BACKEND_API_BASE_URL}/ingredients`)
      if (response.ok) {
        const data = await response.json()
        setIngredients(data)
      } else {
        console.error('Failed to load ingredients')
      }
    } catch (error) {
      console.error('Failed to load ingredients', error)
    }
  }, [ingredients])

  return (
    <IngredientsContext.Provider value={{ ingredients, loadIngredients }}>
      {children}
    </IngredientsContext.Provider>
  )
}