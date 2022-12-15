package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.domains.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    private static final Integer ID_VALUE = 1;
    private static final Short UOM_ID = 10;
    private static final float AMOUNT_VALUE = 12f;
    private static final String DESCRIPTION_VALUE = "description";
    IngredientToIngredientCommand toIngredientCommand;

    @BeforeEach
    void setUp() {
        toIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
    }

    @Test
    void testNullParameter(){
        assertNull(toIngredientCommand.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(toIngredientCommand.convert(new Ingredient()));
    }

    @Test
    void convert() {
        //given
        var ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT_VALUE);
        ingredient.setDescription(DESCRIPTION_VALUE);
        var uintOfMeasure = new UnitOfMeasure();
        uintOfMeasure.setId(UOM_ID);
        ingredient.setUnitOfMeasure(uintOfMeasure);

        //when
        var ingredientCommand = toIngredientCommand.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNotNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ingredient.getId(), ingredientCommand.getId());
        assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
        assertEquals(ingredient.getUnitOfMeasure().getId(), ingredientCommand.getUnitOfMeasure().getId());
    }

    @Test
    void convertWithNullUOM(){
        //given
        var ingredient = new Ingredient();
        ingredient.setId(ID_VALUE);
        ingredient.setAmount(AMOUNT_VALUE);
        ingredient.setDescription(DESCRIPTION_VALUE);

        //when
        var ingredientCommand = toIngredientCommand.convert(ingredient);

        //then
        assertNotNull(ingredientCommand);
        assertNull(ingredientCommand.getUnitOfMeasure());
        assertEquals(ingredient.getId(), ingredientCommand.getId());
        assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
    }
}