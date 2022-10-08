package dev.moreno.recipe_project.repositories;

import dev.moreno.recipe_project.domains.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository<Ingredient, Integer> {
}
