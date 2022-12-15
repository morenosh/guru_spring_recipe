package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.converters.RecipeToRecipeCommand;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RecipeServiceIT {

    private static final String NEW_DIRECTION_VALUE = "new directions";
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeToRecipeCommand toRecipeCommand;

    @Autowired
    RecipeService recipeService;

    @Transactional
    @Test
    void saveRecipeDirection() {
        //given
        var recipes = recipeRepository.findAll();
        var testRecipe = recipes.iterator().next();
        var recipeCommand = toRecipeCommand.convert(testRecipe);

        //when
        recipeCommand.setDirections(NEW_DIRECTION_VALUE);
        var savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        //then
        assertNotNull(savedRecipeCommand);
        assertEquals(NEW_DIRECTION_VALUE, savedRecipeCommand.getDirections());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
    }
}