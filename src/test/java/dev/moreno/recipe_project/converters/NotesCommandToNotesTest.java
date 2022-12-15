package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.NotesCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotesCommandToNotesTest {

    private static final Long ID_VALUE = 1L;
    private static final String RECIPE_NOTE = "recipe note";

    NotesCommandToNotes toNote;

    @BeforeEach
    void setUp() {
        toNote = new NotesCommandToNotes();
    }

    @Test
    void testNullParameter(){
        assertNull(toNote.convert(null));
    }

    @Test
    void testNotEmpty(){
        assertNotNull(toNote.convert(new NotesCommand()));
    }

    @Test
    void convert() {
        //given
        var noteCommand = new NotesCommand();
        noteCommand.setId(ID_VALUE);
        noteCommand.setRecipeNotes(RECIPE_NOTE);

        //when
        var note = toNote.convert(noteCommand);

        //then
        assertNotNull(note);
        assertEquals(note.getId(), noteCommand.getId());
        assertEquals(note.getNotes(), noteCommand.getRecipeNotes());
    }
    
}