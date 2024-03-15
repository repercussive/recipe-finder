import { useIngredients } from '../context/IngredientsContext'

function IngredientsInput() {
  const { ingredients, loadIngredients } = useIngredients()

  if (ingredients.length === 0) {
    return <button onClick={loadIngredients}>Load ingredients</button>
  }

  return (
    <>
      <h3>Ingredients:</h3>
      <ul>
        {ingredients.map((ingredient) => (
          <li key={ingredient.id}>{ingredient.name}</li>
        ))}
      </ul>
    </>
  )
}

export default IngredientsInput