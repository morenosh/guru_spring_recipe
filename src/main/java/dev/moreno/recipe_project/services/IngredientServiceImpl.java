package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.converters.IngredientCommandToIngredient;
import dev.moreno.recipe_project.converters.IngredientToIngredientCommand;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.repositories.IngredientRepository;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import dev.moreno.recipe_project.repositories.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    final private IngredientRepository ingredientRepository;
    final private RecipeRepository recipeRepository;
    final private IngredientToIngredientCommand toIngredientCommand;
    final private IngredientCommandToIngredient toIngredient;
    final private UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository,
                                 RecipeRepository recipeRepository,
                                 IngredientToIngredientCommand toIngredientCommand,
                                 IngredientCommandToIngredient toIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.toIngredientCommand = toIngredientCommand;
        this.toIngredient = toIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    @Transactional
    public IngredientCommand findCommandByIdAndRecipeId(Integer id, Long recipeId) {
        var recipe = recipeRepository.findById(recipeId);
        if (recipe.isEmpty()) {
            //todo impl error handling
            log.error("recipe id not found: " + recipeId);
        }
        else{
            var ingredientSet = recipe.get().getIngredients();

            var ingredientCommand =
                    ingredientSet.stream().filter(a -> a.getId().equals(id))
                            .map(toIngredientCommand::convert).findFirst();

            if (ingredientCommand.isEmpty()) {
                //todo impl error handling
                log.error("Ingredient id not found: " + id);
            }
            else{
                return ingredientCommand.get();
            }
        }
        return null;
    }


    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        var oldRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());
        if (oldRecipe.isEmpty()) {
            //todo throw error if not found!
            log.error("Recipe is not found for id = " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        }
        var ingredientOptional = oldRecipe.get()
                .getIngredients()
                .stream()
                .filter(a -> a.getId().equals(ingredientCommand.getId())).findFirst();
        if (ingredientOptional.isEmpty()) {
            oldRecipe.get().addIngredient(toIngredient.convert(ingredientCommand));
        } else {
            var ingredient = ingredientOptional.get();
            ingredient.setDescription(ingredientCommand.getDescription());
            ingredient.setAmount(ingredientCommand.getAmount());

            var uomID = ingredientCommand.getUnitOfMeasure().getId();
            ingredient.setUnitOfMeasure(unitOfMeasureRepository
                    .findById(uomID)
                    .orElseThrow(() -> new RuntimeException("UOM not found with id = " + uomID)));
        }
        var savedRecipe = recipeRepository.save(oldRecipe.get());

        Ingredient savedIngredient;
        if (ingredientCommand.getId() != null) {
            savedIngredient = savedRecipe.getIngredients()
                    .stream().filter(
                            a -> a.getId().equals(ingredientCommand.getId())
                    ).findFirst().orElseThrow(() -> new RuntimeException("ingredient not found"));
        } else {
            savedIngredient = savedRecipe.getIngredients()
                    .stream().filter(
                            a -> a.getDescription().equals(ingredientCommand.getDescription()) &&
                                    a.getAmount().equals(ingredientCommand.getAmount()) &&
                                    a.getUnitOfMeasure().getId().equals(ingredientCommand.getUnitOfMeasure().getId())
                    ).findFirst().orElseThrow(() -> new RuntimeException("ingredient not found"));
        }
        return toIngredientCommand.convert(savedIngredient);
    }

    @Override
    @Transactional
    public void deleteByIdAndRecipeId(int ingredientId, long recipeId) {

        var recipe = recipeRepository.findById(recipeId);
        if (recipe.isPresent()) {
            log.debug("found recipe");
            var ingredient = recipe.get().getIngredients()
                    .stream().filter(a -> a.getId().equals(ingredientId)).findFirst();
            if (ingredient.isPresent()){
                log.debug("found Ingredient");
                recipe.get().getIngredients().remove(ingredient.get());
                recipeRepository.save(recipe.get());
                ingredientRepository.deleteById(ingredientId);
            } else {
                log.debug("ingredient not found");
            }
        } else {
            log.debug("Recipe Id Not found. Id:" + recipeId);
        }
    }
}
