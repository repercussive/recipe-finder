import IngredientsInput from '@src/components/IngredientsInput'
import Spacer from '@src/components/Spacer'
import SuggestedRecipesList from '@src/components/SuggestedRecipesList'

const FindRecipesPage: React.FC = () => {
  return (
    <>
      <IngredientsInput />
      <Spacer mb="1.75rem" />
      <SuggestedRecipesList />
    </>

  )
}

export default FindRecipesPage