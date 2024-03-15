import { useRecipeSearch } from '@src/context/RecipeSearchContext'
import { Link } from 'wouter'

const SuggestedRecipesList: React.FC = () => {
  const { recipeResults } = useRecipeSearch()

  if (recipeResults == null) return null

  return (
    <div>
      <h3>Suggested recipes</h3>
      {recipeResults.length === 0 && <p>No suitable recipes found.</p>}
      <ul>
        {recipeResults.map((recipe) => (
          <li key={recipe.id}>
            <Link href={`/recipes/${recipe.id}`}>{recipe.name}</Link>
          </li>
        ))}
      </ul>
    </div>
  )
}

export default SuggestedRecipesList