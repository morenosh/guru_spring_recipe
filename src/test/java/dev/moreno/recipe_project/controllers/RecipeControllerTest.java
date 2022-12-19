package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class RecipeControllerTest {

    @Mock
    RecipeService recipeService;

    @InjectMocks
    RecipeController recipeController;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testShowRecipe() throws Exception {
        var mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        Mockito.when(recipeService.findById(Mockito.anyLong())).thenReturn(Recipe.builder().build());

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/show/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/show"));
    }

    @Test
    void testAddNewRecipe() throws Exception {
        var mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/new"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"));
    }

    @Test
    void testSaveOrUpdate() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();
        var sampleRecipeCommand = new RecipeCommand();
        sampleRecipeCommand.setId(5L);

        //when
        Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(sampleRecipeCommand);

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/recipe", new RecipeCommand()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/recipe/5/show/"));
    }

    @Test
    void testGetUpdateView() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        //when
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(new RecipeCommand());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/update"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("recipe/recipeform"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
    }

    @Test
    void testDeleteOneRecipe() throws Exception{
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(recipeController).build();

        //when
        // no given! because service return void

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/5/delete"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"));
    }

}
