import IngredientsInput from './components/IngredientsInput'
import { IngredientsProvider } from './context/IngredientsContext'

function App() {
  return (
    <>
      <h1>RecipeFinder</h1>
      <IngredientsProvider>
        <IngredientsInput />
      </IngredientsProvider>
    </>
  )
}

export default App
