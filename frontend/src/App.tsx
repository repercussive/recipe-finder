import { AppProvider } from '@src/context/AppContext'
import { Route, Switch } from 'wouter'
import FindRecipesPage from '@src/pages/FindRecipesPage'
import RecipePage from '@src/pages/RecipePage'

function App() {
  return (
    <>
      <h1>RecipeFinder</h1>
      <main>
        <AppProvider>
          <Switch>
            <Route path="/">
              <FindRecipesPage />
            </Route>

            <Route path="/recipes/:id">
              <RecipePage />
            </Route>

            <Route>
              <h1>404 Not found</h1>
            </Route>
          </Switch>
        </AppProvider>
      </main>
    </>
  )
}

export default App