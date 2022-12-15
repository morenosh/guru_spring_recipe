package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.domains.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private static final Short SHORT_VALUE = 1;
    private static final String DESCRIPTION_VALUE = "description";

    CategoryToCategoryCommand converter;

    @BeforeEach
    void setUp() {
        converter = new CategoryToCategoryCommand();
    }

    @Test
    void testNullParameter(){
        assertNull(converter.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(converter.convert(new Category()));
    }

    @Test
    void convert() {
        //given
        Category category = new Category();
        category.setId(SHORT_VALUE);
        category.setDescription(DESCRIPTION_VALUE);

        //when
        var categoryCommand = converter.convert(category);

        //then
        assertNotNull(categoryCommand);
        assertEquals(categoryCommand.getId(), category.getId());
        assertEquals(categoryCommand.getDescription(), category.getDescription());
    }
}