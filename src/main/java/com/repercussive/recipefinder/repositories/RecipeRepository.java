package com.repercussive.recipefinder.repositories;

import com.repercussive.recipefinder.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>, RecipeRepositoryCustom {
    Optional<Recipe> findByNameIgnoreCase(String name);
}
