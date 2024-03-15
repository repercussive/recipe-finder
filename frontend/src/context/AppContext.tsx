import { IngredientsProvider } from '@context/IngredientsContext'
import { RecipeSearchProvider } from '@src/context/RecipeSearchContext'

export const AppProvider: React.FC<React.PropsWithChildren> = ({ children }) => {
  return (
    <IngredientsProvider>
      <RecipeSearchProvider>
        {children}
      </RecipeSearchProvider>
    </IngredientsProvider>
  )
}