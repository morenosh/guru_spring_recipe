package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.domains.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureToUnitOfMeasureCommandTest {

    private static final Short ID_VALUE = 1;
    private static final String DESCRIPTION_VALUE = "description";
    UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    @BeforeEach
    void setUp() {
        toUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    void testNullParameter(){
        assertNull(toUnitOfMeasureCommand.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(toUnitOfMeasureCommand.convert(new UnitOfMeasure()));
    }

    @Test
    void convert() {
        //given
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setId(ID_VALUE);
        unitOfMeasure.setUom(DESCRIPTION_VALUE);

        //when
        var unitOfMeasureCommand = toUnitOfMeasureCommand.convert(unitOfMeasure);

        //then
        assertNotNull(unitOfMeasureCommand);
        assertEquals(unitOfMeasure.getId(), unitOfMeasureCommand.getId());
        assertEquals(unitOfMeasure.getUom(), unitOfMeasureCommand.getDescription());
    }
}