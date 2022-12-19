package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.domains.BaseEntity;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.services.RecipeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class IndexControllerTest {

    Random rnd = new Random();

    @Mock
    RecipeService recipeService;
    @Mock
    Model model;
    @Mock
    BaseEntity baseEntity;

    @Captor
    ArgumentCaptor<Set<Recipe>> setOfRecipeCaptor;

    IndexController controller;
    AutoCloseable ac;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        baseEntity = Mockito.mock(BaseEntity.class);
        recipeService = Mockito.mock(RecipeService.class);
        model = Mockito.mock(Model.class);
        controller = new IndexController(recipeService);
    }

    @Test
    void testListRecipe(){
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        try {
            mockMvc.perform(MockMvcRequestBuilders.get("/"))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.view().name("index"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getMainPage() {
        Mockito.when(baseEntity.getId()).thenReturn(rnd.nextLong());
        var recipeSet = new HashSet<Recipe>();
        var r1 = new Recipe();
        var r2 = new Recipe();
        recipeSet.add(r1);
        recipeSet.add(r2);

        Mockito.when(recipeService.getRecipes()).thenReturn(recipeSet);
        Mockito.when(model.addAttribute("recipeList", new HashSet<Recipe>())).thenReturn(model);

        Assertions.assertEquals(controller.getMainPage(model), "index");
        Mockito.verify(recipeService, Mockito.times(1)).getRecipes();
        Mockito.verify(model, Mockito.times(1)).addAttribute(Mockito.eq("recipeList"), setOfRecipeCaptor.capture());
        Assertions.assertEquals(setOfRecipeCaptor.getValue(), recipeSet);
    }

}