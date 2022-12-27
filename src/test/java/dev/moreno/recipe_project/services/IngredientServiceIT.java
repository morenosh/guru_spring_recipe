package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.domains.Difficulty;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.repositories.IngredientRepository;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("NewClassNamingConvention")
@SpringBootTest
public class IngredientServiceIT {

    @Autowired
    IngredientService ingredientService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    IngredientRepository ingredientRepository;

    @Test
    @Transactional
    void deleteByIdAndRecipeIdWhenRecipeAndIngredientAvailable() {
        //given
        var recipe = Recipe.builder().categories(new HashSet<>()).cookTimeMinutes(10L)
                .difficulty(Difficulty.HARD).directions("sdfasfd").ingredients(new HashSet<>()).build();
        var ingredient = Ingredient.builder().amount(1f).build();
        recipe.addIngredient(ingredient);
        recipe = recipeRepository.save(recipe);
        var ingredientSize = recipe.getIngredients().size();
        assertEquals(ingredientSize, 1);

        //when
        ingredientService.deleteByIdAndRecipeId(ingredient.getId(), recipe.getId());

        //then
        var newRecipeOptional = recipeRepository.findById(recipe.getId());
        assertTrue(newRecipeOptional.isPresent());
        assertEquals(ingredientSize - 1, newRecipeOptional.get().getIngredients().size());
        var deletedIngredient = ingredientRepository.findById(ingredient.getId());
        assertFalse(deletedIngredient.isPresent());
    }
}
