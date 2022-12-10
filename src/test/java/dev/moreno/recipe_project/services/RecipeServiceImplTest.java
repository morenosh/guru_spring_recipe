package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RecipeServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    RecipeServiceImpl recipeService;

    Set<Recipe> RecipeSamples = new HashSet<>();
    Recipe recipeSample1 = Recipe.builder().build();
    Recipe recipeSample2 = Recipe.builder().build();

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recipeSample1, "id", 1L);
        ReflectionTestUtils.setField(recipeSample2, "id", 2L);
        RecipeSamples.add(recipeSample1);
        RecipeSamples.add(recipeSample2);
    }

    @Test
    void getRecipes() {
        Mockito.when(recipeRepository.findAll()).thenReturn(RecipeSamples);

        var recipes = recipeService.getRecipes();
        assertEquals(recipes.size(), 2);
    }

    @Test
    void findByIdFound() {
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipeSample1));

        var r = recipeService.findById(1L);
        assertEquals(r, recipeSample1);
    }

    @Test
    void findByIdNotFound() {
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        var r = recipeService.findById(1L);
        assertNull(r);
    }
}