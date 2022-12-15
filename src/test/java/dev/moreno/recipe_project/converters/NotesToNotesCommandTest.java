package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.domains.Notes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesToNotesCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String NOTES = "notes";
    NotesToNotesCommand toNotesCommand;

    @BeforeEach
    void setUp() {
        toNotesCommand = new NotesToNotesCommand();
    }

    @Test
    void testNullParameter(){
        assertNull(toNotesCommand.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(toNotesCommand.convert(new Notes()));
    }

    @Test
    void convert() {
        //given
        var notes = new Notes();
        notes.setId(ID_VALUE);
        notes.setNotes(NOTES);

        //when
        var notesCommand = toNotesCommand.convert(notes);

        //then
        assertNotNull(notesCommand);
        assertEquals(notes.getId(), notesCommand.getId());
        assertEquals(notes.getNotes(), notesCommand.getRecipeNotes());
    }
}