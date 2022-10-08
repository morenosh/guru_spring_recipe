package dev.moreno.recipe_project.repositories;

import dev.moreno.recipe_project.domains.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
