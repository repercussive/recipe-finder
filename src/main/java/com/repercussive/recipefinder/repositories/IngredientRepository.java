package com.repercussive.recipefinder.repositories;

import com.repercussive.recipefinder.models.Ingredient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IngredientRepository extends CrudRepository<Ingredient, Long> {
    Optional<Ingredient> findByName(String name);
}
