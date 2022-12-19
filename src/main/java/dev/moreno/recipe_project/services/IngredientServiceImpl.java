package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.converters.IngredientToIngredientCommand;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.repositories.IngredientRepository;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    final private IngredientRepository ingredientRepository;
    final private RecipeRepository recipeRepository;
    final private IngredientToIngredientCommand toIngredientCommand;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand toIngredientCommand) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.toIngredientCommand = toIngredientCommand;
    }

    @Override
    public IngredientCommand findCommandByIdAndRecipeId(Integer id, Long recipeId) {
        var recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()){
            //todo impl error handling
            log.error("recipe id not found: " + recipeId);
        }

        var ingredientSet = recipe.get().getIngredients();

        var ingredientCommand =
                ingredientSet.stream().filter(a->a.getId().equals(id))
                .map(toIngredientCommand::convert).findFirst();

        if (ingredientCommand.isEmpty()){
            //todo impl error handling
            log.error("Ingredient id not found: " + id);
        }

        return ingredientCommand.get();
    }
}
