package com.repercussive.recipefinder.repositories;

import com.repercussive.recipefinder.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
