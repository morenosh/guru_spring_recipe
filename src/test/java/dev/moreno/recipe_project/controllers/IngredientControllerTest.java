package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.repositories.UnitOfMeasureRepository;
import dev.moreno.recipe_project.services.IngredientService;
import dev.moreno.recipe_project.services.RecipeService;
import dev.moreno.recipe_project.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    IngredientController ingredientController;

    @Test
    void listIngredients() throws Exception {
        //given
        var recipeCommand = new RecipeCommand();
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        //when
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/2/ingredients"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    void showIngredient() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        var ingredientCommand = new IngredientCommand();

        //when
        Mockito.when(ingredientService.findCommandByIdAndRecipeId(Mockito.anyInt(), Mockito.anyLong()))
                .thenReturn(new IngredientCommand());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/3/ingredient/4/show"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/recipe/ingredient/show"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"));


    }

    @Test
    void updateIngredient() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();

        //when
        Mockito.when(ingredientService.findCommandByIdAndRecipeId(Mockito.anyInt(), Mockito.anyLong())).thenReturn(new IngredientCommand());
        Mockito.when(unitOfMeasureService.listAllUnitOfMeasures()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/ingredient/3/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/ingredient/ingredientform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("ingredient"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("uomList"));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        //given
        var mockMvn = MockMvcBuilders.standaloneSetup(ingredientController).build();
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setId(3);

        //when
        Mockito.when(ingredientService.saveIngredientCommand(Mockito.any())).thenReturn(ingredientCommand);

        //then
        mockMvn.perform(MockMvcRequestBuilders.post("/recipe/1/ingredient"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/1/ingredient/3/show"));
    }
}