package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.UnitOfMeasureCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final Short ID_VALUE = 1;
    private static final String DESCRIPTION_VALUE = "description";
    UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    @BeforeEach
    void setUp() {
        toUnitOfMeasure = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    void testNullParameter(){
        assertNull(toUnitOfMeasure.convert(null));
    }

    @Test
    void testNotEmpty() {
        assertNotNull(toUnitOfMeasure.convert(new UnitOfMeasureCommand()));
    }

    @Test
    void convert() {
        //given
        var unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(ID_VALUE);
        unitOfMeasureCommand.setDescription(DESCRIPTION_VALUE);

        //when
        var unitOfMeasure = toUnitOfMeasure.convert(unitOfMeasureCommand);

        //then
        assertNotNull(unitOfMeasure);
        assertEquals(unitOfMeasure.getId(), unitOfMeasureCommand.getId());
        assertEquals(unitOfMeasure.getUom(), unitOfMeasureCommand.getDescription());
    }
}