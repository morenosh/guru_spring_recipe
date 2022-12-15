package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.domains.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes toNotes;
    private final CategoryCommandToCategory toCategory;
    private final IngredientCommandToIngredient toIngredient;

    public RecipeCommandToRecipe(NotesCommandToNotes toNotes,
                                 CategoryCommandToCategory toCategory,
                                 IngredientCommandToIngredient toIngredient) {
        this.toNotes = toNotes;
        this.toCategory = toCategory;
        this.toIngredient = toIngredient;
    }

    @Synchronized
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null)
            return null;

        final Recipe recipe = new Recipe();

        recipe.setId(source.getId());
        recipe.setNote(toNotes.convert(source.getNotes()));
        recipe.setTitle(source.getTitle());
        recipe.setUrl(source.getUrl());
        recipe.setSource(source.getSource());
        recipe.setServingPeople(source.getServingPeople());
        recipe.setDirections(source.getDirections());
        recipe.setPrepTimeMinutes(source.getPrepTimeMinutes());
        recipe.setCookTimeMinutes(source.getCookTimeMinutes());
        recipe.setDifficulty(source.getDifficulty());

        if (source.getCategories() != null && !source.getCategories().isEmpty()) {
            source.getCategories().forEach(a-> recipe.getCategories().add(toCategory.convert(a)));
        }

        if (source.getIngredients() != null && !source.getIngredients().isEmpty()){
            source.getIngredients().forEach(a->recipe.getIngredients().add(toIngredient.convert(a)));
        }

        return recipe;
    }
}

