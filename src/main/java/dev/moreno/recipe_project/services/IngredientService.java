package dev.moreno.recipe_project.services;


import dev.moreno.recipe_project.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findCommandByIdAndRecipeId(Integer id, Long recipeId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
