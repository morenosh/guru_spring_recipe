package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.commands.UnitOfMeasureCommand;
import dev.moreno.recipe_project.converters.IngredientCommandToIngredient;
import dev.moreno.recipe_project.converters.IngredientToIngredientCommand;
import dev.moreno.recipe_project.domains.BaseEntity;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.domains.UnitOfMeasure;
import dev.moreno.recipe_project.repositories.IngredientRepository;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import dev.moreno.recipe_project.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    IngredientRepository ingredientRepository;

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Mock
    IngredientToIngredientCommand toIngredientCommand;

    @Mock
    IngredientCommandToIngredient toIngredient;

    @InjectMocks
    IngredientServiceImpl ingredientService;

    @Captor
    ArgumentCaptor<Recipe> recipeArgumentCaptor;

    @Captor
    ArgumentCaptor<Integer> integerArgumentCaptor;

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
        var ingredient = ingredientService.findCommandByIdAndRecipeId(6, 3L);

        //then
        assertNotNull(ingredient);
        assertEquals(6, ingredient.getId());
        assertEquals(3L, ingredient.getRecipeId());
    }

    @Test
    void saveIngredientCommandWhenRecipeAndIngredientAvailable() {
        //given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1);
        ingredientCommand.setRecipeId(1L);
        ingredientCommand.setDescription("description");
        ingredientCommand.setAmount(10f);

        var uomCommand = new UnitOfMeasureCommand();
        uomCommand.setId((short) 1);
        uomCommand.setDescription("uomDescription");
        ingredientCommand.setUnitOfMeasure(uomCommand);

        var recipe = Recipe.builder().id(1L).ingredients(new HashSet<>()).build();
        var ingredient = Ingredient.builder()
                .id(ingredientCommand.getId()).recipe(recipe)
                .description(ingredientCommand.getDescription()).build();
        recipe.getIngredients().add(ingredient);

        Mockito.when(recipeRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(recipe));

        var uom = new UnitOfMeasure();
        uom.setUom(uomCommand.getDescription());
        uom.setId(uomCommand.getId());
        Mockito.when(unitOfMeasureRepository.findById(Mockito.anyShort())).thenReturn(Optional.of(uom));
        Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(recipe);
        Mockito.when(toIngredientCommand.convert(Mockito.any())).thenReturn(ingredientCommand);

        //when
        var savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        //then
        assertNotNull(savedCommand);
        assertEquals(1, savedCommand.getId());
        assertEquals(1L, savedCommand.getRecipeId());
        assertEquals("description", savedCommand.getDescription());
        assertEquals("uomDescription", savedCommand.getUnitOfMeasure().getDescription());
        assertEquals(10f, savedCommand.getAmount());
    }

    @Test
    void saveIngredientCommandWhenRelatedRecipeNotAvailable(){
        //given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(1L);
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.empty());

        //when
        var savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertNotNull(savedCommand);
        assertNull(savedCommand.getId());
        assertNull(savedCommand.getRecipeId());
        assertNull(savedCommand.getAmount());
        assertNull(savedCommand.getDescription());
        assertNull(savedCommand.getUnitOfMeasure());
    }

    @Test
    void saveIngredientCommandWhenRelatedRecipeNotContainIngredient(){
        //given
        var recipe = new Recipe();
        recipe.setId(1L);
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipe.getId());
        ingredientCommand.setId(1);
        Mockito.when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        var ingredient = new Ingredient();
        ingredient.setId(ingredientCommand.getId());
        var recipeIncludeIngredient = new Recipe();
        recipeIncludeIngredient.setId(recipe.getId());
        recipeIncludeIngredient.addIngredient(ingredient);
        Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(recipeIncludeIngredient);

        Mockito.when(toIngredient.convert(Mockito.any())).thenReturn(ingredient);
        Mockito.when(toIngredientCommand.convert(Mockito.any())).thenReturn(ingredientCommand);

        //when
        var savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        assertNotNull(savedCommand);
        assertEquals(ingredientCommand.getId(), savedCommand.getId());
        assertEquals(recipeIncludeIngredient.getId(), savedCommand.getRecipeId());
    }

    @Test
    void deleteByIdAndRecipeIdWhenRecipeAndIngredientAvailable() {
        //given
        var recipeId = 2L;
        var ingredientId = 1;
        var recipe = new Recipe();
        var ingredient = new Ingredient();
        recipe.setId(recipeId);
        ingredient.setId(ingredientId);
        recipe.addIngredient(ingredient);
        var oldIngredientSize = recipe.getIngredients().size();
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        //when
        ingredientService.deleteByIdAndRecipeId(ingredientId, recipeId);

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).findById(recipeId);

        Mockito.verify(recipeRepository, Mockito.times(1)).save(recipeArgumentCaptor.capture());
        var newRecipe = recipeArgumentCaptor.getValue();

        Mockito.verify(ingredientRepository, Mockito.times(1)).deleteById(integerArgumentCaptor.capture());
        assertEquals(ingredientId, integerArgumentCaptor.getValue());

        assertNotNull(newRecipe);
        assertEquals(oldIngredientSize - 1, newRecipe.getIngredients().size());
        assertFalse(newRecipe.getIngredients().stream().map(BaseEntity::getId).anyMatch(c->c.equals(ingredientId)));
    }
}