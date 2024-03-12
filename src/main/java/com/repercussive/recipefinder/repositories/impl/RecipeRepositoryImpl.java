package com.repercussive.recipefinder.repositories.impl;

import com.repercussive.recipefinder.models.Ingredient;
import com.repercussive.recipefinder.models.Recipe;
import com.repercussive.recipefinder.repositories.RecipeRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RecipeRepositoryImpl implements RecipeRepositoryCustom {
    private final int MAX_PAGE_SIZE = 10;

    private final EntityManager entityManager;

    public RecipeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Recipe> findBestMatchingRecipes(List<Ingredient> ingredients, int pageNumber, int pageSize) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> query = cb.createQuery(Recipe.class);
        Root<Recipe> recipe = query.from(Recipe.class);

        Subquery<Long> subquery = query.subquery(Long.class);
        Root<Recipe> subqueryRecipe = subquery.correlate(recipe);
        Join<Recipe, Ingredient> subqueryIngredients = subqueryRecipe.join("ingredients");
        subquery.select(cb.count(subqueryIngredients))
                .where(subqueryIngredients.in(ingredients));

        query.where(cb.greaterThan(subquery.getSelection(), 0L))
                .orderBy(cb.desc(subquery.getSelection()));

        TypedQuery<Recipe> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * pageSize);
        typedQuery.setMaxResults(Math.min(pageSize, MAX_PAGE_SIZE));

        return typedQuery.getResultList();
    }
}
