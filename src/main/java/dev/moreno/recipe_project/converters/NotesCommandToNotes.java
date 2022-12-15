package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.NotesCommand;
import dev.moreno.recipe_project.domains.Notes;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class NotesCommandToNotes implements Converter<NotesCommand, Notes> {

    @Synchronized
    @Override
    public Notes convert(NotesCommand source) {

        if (source == null)
            return null;

        final Notes notes = new Notes();

        notes.setId(source.getId());
        notes.setNotes(source.getRecipeNotes());

        return notes;
    }
}
