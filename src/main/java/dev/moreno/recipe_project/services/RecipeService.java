package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.domains.Recipe;

import java.util.Set;

public interface RecipeService {
    Set<Recipe> getRecipes();
}