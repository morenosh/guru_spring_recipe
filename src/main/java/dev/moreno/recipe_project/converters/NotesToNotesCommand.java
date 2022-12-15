package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.NotesCommand;
import dev.moreno.recipe_project.domains.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesToNotesCommand implements Converter<Notes, NotesCommand> {

    @Synchronized
    @Override
    public NotesCommand convert(Notes source) {

        if (source == null)
            return null;

        final NotesCommand notesCommand = new NotesCommand();

        notesCommand.setId(source.getId());
        notesCommand.setRecipeNotes(source.getNotes());

        return notesCommand;
    }
}
