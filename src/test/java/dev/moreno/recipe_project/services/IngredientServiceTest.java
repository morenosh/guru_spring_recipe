package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.converters.IngredientToIngredientCommand;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.repositories.IngredientRepository;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientToIngredientCommand toIngredientCommand;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Test
    void findCommandByIdAndRecipeId() {
        //given
        var ingredient1 = Ingredient.builder().id(5).build();
        var ingredient2 = Ingredient.builder().id(6).build();
        var recipe = Recipe.builder().id(3L).ingredients(Set.of(ingredient1, ingredient2)).build();
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(6);
        ingredientCommand.setRecipeId(3L);

        //when
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));
        Mockito.when(toIngredientCommand.convert(ingredient2)).thenReturn(ingredientCommand);

        //then
        var ingredient = ingredientService.findCommandByIdAndRecipeId(6, 3L);
        assertNotNull(ingredient);
        assertEquals(6, ingredient.getId());
        assertEquals(3L, ingredient.getRecipeId());
    }
}