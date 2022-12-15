package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.CategoryCommand;
import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.commands.NotesCommand;
import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.domains.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeToRecipeCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String DIRECTION_VALUE = "direction";
    private static final String SOURCE_VALUE = "source";
    private static final String TITLE = "title";
    private static final Short SERVING_PEOPLE = 4;
    private static final String URL_VALUE = "url://url.url";
    private static final Long PREPARATION_TIME_VALUE = 10L;
    private static final Long COOK_TIME_VALUE = 15L;
    private static final Difficulty DIFFICULTY_VALUE = Difficulty.HARD;
    private static final Short CATEGORY_ID_VALUE = 5;
    private static final Short CATEGORY_ID_VALUE2 = 7;
    private static final Integer INGREDIENT_ID1 = 3;
    private static final Integer INGREDIENT_ID2 = 43;
    private static final Long NOTE_ID = 2L;

    RecipeToRecipeCommand toRecipeCommand;

    @BeforeEach
    void setUp() {
        toRecipeCommand = new RecipeToRecipeCommand(new NotesToNotesCommand(),
                new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()));
    }

    @Test
    void testNullParameter(){
        assertNull(toRecipeCommand.convert(null));
    }

    @Test
    void testEmptyParameter(){
        assertNotNull(toRecipeCommand.convert(new Recipe()));
    }

    @Test
    void convert() {
        //given
        var recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setDirections(DIRECTION_VALUE);
        recipe.setSource(SOURCE_VALUE);
        recipe.setTitle(TITLE);
        recipe.setServingPeople(SERVING_PEOPLE);
        recipe.setUrl(URL_VALUE);
        recipe.setPrepTimeMinutes(PREPARATION_TIME_VALUE);
        recipe.setCookTimeMinutes(COOK_TIME_VALUE);
        recipe.setDifficulty(DIFFICULTY_VALUE);

        var category1 = new Category();
        category1.setId(CATEGORY_ID_VALUE);

        var category2 = new Category();
        category2.setId(CATEGORY_ID_VALUE2);

        recipe.setCategories(Set.of(category1, category2));

        var ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID1);

        var ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID2);

        recipe.getIngredients().add(ingredient1);
        recipe.getIngredients().add(ingredient2);

        var note = new Notes();
        note.setId(NOTE_ID);

        recipe.setNote(note);

        //when
        var recipeCommand = toRecipeCommand.convert(recipe);

        //then
        assertNotNull(recipe);
        assertEquals(recipe.getId(), recipeCommand.getId());
        assertEquals(recipe.getPrepTimeMinutes(), recipeCommand.getPrepTimeMinutes());
        assertEquals(recipe.getServingPeople(), recipeCommand.getServingPeople());
        assertEquals(recipe.getDirections(), recipeCommand.getDirections());
        assertEquals(recipe.getSource(), recipeCommand.getSource());
        assertEquals(recipe.getTitle(), recipeCommand.getTitle());
        assertEquals(recipe.getUrl(), recipeCommand.getUrl());
        assertEquals(recipe.getCookTimeMinutes(), recipeCommand.getCookTimeMinutes());
        assertEquals(recipe.getDifficulty(), recipeCommand.getDifficulty());
        assertEquals(recipe.getNote().getId(), NOTE_ID);
        assertEquals(recipe.getIngredients().size(), 2);
        assertEquals(recipe.getCategories().size(), 2);
    }
}