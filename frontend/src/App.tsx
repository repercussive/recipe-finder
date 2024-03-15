import { IngredientsProvider } from '@context/IngredientsContext'
import IngredientsInput from '@components/IngredientsInput'

function App() {
  return (
    <>
      <h1>RecipeFinder</h1>
      <main>
        <IngredientsProvider>
          <IngredientsInput />
        </IngredientsProvider>
      </main>
    </>
  )
}

export default App
