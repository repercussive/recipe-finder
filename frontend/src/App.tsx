import { AppProvider } from '@src/context/AppContext'
import IngredientsInput from '@components/IngredientsInput'
import SuggestedRecipesList from '@src/components/SuggestedRecipesList'

function App() {
  return (
    <>
      <h1>RecipeFinder</h1>
      <main>
        <AppProvider>
          <IngredientsInput />
          <SuggestedRecipesList />
        </AppProvider>
      </main>
    </>
  )
}

export default App