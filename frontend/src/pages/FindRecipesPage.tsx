import IngredientsInput from '@src/components/IngredientsInput'
import SuggestedRecipesList from '@src/components/SuggestedRecipesList'

const FindRecipesPage: React.FC = () => {
  return (
    <>
      <IngredientsInput />
      <SuggestedRecipesList />
    </>

  )
}

export default FindRecipesPage