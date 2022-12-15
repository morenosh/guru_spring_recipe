package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.commands.UnitOfMeasureCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {

    private static final Integer ID_VALUE = 1;
    private static final float AMOUNT_VALUE = 10f;
    private static final String DESCRIPTION_VALUE = "description";
    private static final Short UOM_ID = 2;

    IngredientCommandToIngredient toIngredient;

    @BeforeEach
    void setUp() {
        toIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }

    @Test
    void testNullParameter(){
        assertNull(toIngredient.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(toIngredient.convert(new IngredientCommand()));
    }

    @Test
    void convert() {
        //given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT_VALUE);
        ingredientCommand.setDescription(DESCRIPTION_VALUE);
        var unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        ingredientCommand.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        var ingredient = toIngredient.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUnitOfMeasure());
        assertEquals(ingredient.getId(), ingredientCommand.getId());
        assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
        assertEquals(ingredient.getUnitOfMeasure().getId(), ingredientCommand.getUnitOfMeasure().getId());
    }

    @Test
    void convertWithNullUOM(){
        //given
        var ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID_VALUE);
        ingredientCommand.setAmount(AMOUNT_VALUE);
        ingredientCommand.setDescription(DESCRIPTION_VALUE);

        //when
        var ingredient = toIngredient.convert(ingredientCommand);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUnitOfMeasure());
        assertEquals(ingredient.getId(), ingredientCommand.getId());
        assertEquals(ingredient.getAmount(), ingredientCommand.getAmount());
        assertEquals(ingredient.getDescription(), ingredientCommand.getDescription());
    }
}