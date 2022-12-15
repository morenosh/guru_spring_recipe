package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.CategoryCommand;
import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.commands.NotesCommand;
import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.domains.Difficulty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

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

    RecipeCommandToRecipe toRecipe;

    @BeforeEach
    void setUp() {
        toRecipe = new RecipeCommandToRecipe(new NotesCommandToNotes(),
                new CategoryCommandToCategory(),
                new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()));
    }

    @Test
    void testNullParameter(){
        assertNull(toRecipe.convert(null));
    }

    @Test
    void testEmptyParameter(){
        assertNotNull(toRecipe.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        //given
        var recipeCommand = new RecipeCommand();
        recipeCommand.setId(ID_VALUE);
        recipeCommand.setDirections(DIRECTION_VALUE);
        recipeCommand.setSource(SOURCE_VALUE);
        recipeCommand.setTitle(TITLE);
        recipeCommand.setServingPeople(SERVING_PEOPLE);
        recipeCommand.setUrl(URL_VALUE);
        recipeCommand.setPrepTimeMinutes(PREPARATION_TIME_VALUE);
        recipeCommand.setCookTimeMinutes(COOK_TIME_VALUE);
        recipeCommand.setDifficulty(DIFFICULTY_VALUE);

        var categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CATEGORY_ID_VALUE);

        var categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CATEGORY_ID_VALUE2);

        recipeCommand.setCategories(Set.of(categoryCommand1, categoryCommand2));

        var ingredient1 = new IngredientCommand();
        ingredient1.setId(INGREDIENT_ID1);

        var ingredient2 = new IngredientCommand();
        ingredient2.setId(INGREDIENT_ID2);

        recipeCommand.setIngredients(Set.of(ingredient1, ingredient2));

        var noteCommand = new NotesCommand();
        noteCommand.setId(NOTE_ID);

        recipeCommand.setNotes(noteCommand);

        //when
        var recipe = toRecipe.convert(recipeCommand);

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