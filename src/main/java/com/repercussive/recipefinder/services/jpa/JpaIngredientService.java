package com.repercussive.recipefinder.services.jpa;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.repositories.IngredientRepository;
import com.repercussive.recipefinder.services.IngredientService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class JpaIngredientService implements IngredientService {
    private final IngredientRepository ingredientRepository;

    public JpaIngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public Ingredient createIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    @Override
    public Optional<Ingredient> findById(Long id) {
        return ingredientRepository.findById(id);
    }

    @Override
    public Optional<Ingredient> findByName(String name) {
        return ingredientRepository.findByName(name);
    }
}
