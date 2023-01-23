package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.converters.RecipeToRecipeCommand;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.exceptions.NotFoundException;
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

    @Mock
    RecipeToRecipeCommand toRecipeCommand;


    @InjectMocks
    RecipeServiceImpl recipeService;

    Set<Recipe> RecipeSamples = new HashSet<>();
    Recipe recipeSample1 = Recipe.builder().build();
    Recipe recipeSample2 = Recipe.builder().build();

    RecipeCommand recipeCommandSample1;
    RecipeCommand recipeCommandSample2;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(recipeSample1, "id", 1L);
        ReflectionTestUtils.setField(recipeSample2, "id", 2L);
        RecipeSamples.add(recipeSample1);
        RecipeSamples.add(recipeSample2);

        recipeCommandSample1 = new RecipeCommand();
        recipeCommandSample1.setId(recipeSample1.getId());

        recipeCommandSample2 = new RecipeCommand();
        recipeCommandSample2.setId(recipeSample2.getId());
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
        var exception = assertThrows(NotFoundException.class, () -> recipeService.findById(1L));
        assertEquals("Recipe Not Found", exception.getMessage());
    }

    @Test
    void findRecipeCommandById() {
        //given
        Mockito.when(toRecipeCommand.convert(Mockito.any())).thenReturn(recipeCommandSample1);
        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipeSample1));

        //when
        var r = recipeService.findRecipeCommandById(1L);

        //then
        assertNotNull(r);
        assertEquals(recipeCommandSample1.getId(), r.getId());
    }

    @Test
    void deleteRecipeById() {
        //given
        var idToBeDeleted = 5L;

        //when
        recipeService.deleteById(idToBeDeleted);

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).deleteById(idToBeDeleted);

    }
}
