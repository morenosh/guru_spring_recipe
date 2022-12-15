package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.CategoryCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {

    private static final Short SHORT_VALUE = 1;
    private static final String DESCRIPTION_VALUE = "description";

    CategoryCommandToCategory converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryCommandToCategory();
    }

    @Test
    void testNullParameter(){
        assertNull(converter.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(converter.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        //given
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setId(SHORT_VALUE);
        categoryCommand.setDescription(DESCRIPTION_VALUE);

        //when
        var category = converter.convert(categoryCommand);

        //then
        assertNotNull(category);
        assertEquals(category.getId(), categoryCommand.getId());
        assertEquals(category.getDescription(), categoryCommand.getDescription());
    }
}