package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.domains.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findRecipeCommandById(Long id);

    void deleteById(Long id);
}
