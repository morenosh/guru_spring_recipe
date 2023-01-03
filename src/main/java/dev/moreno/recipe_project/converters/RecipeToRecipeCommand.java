package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.domains.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    final private NotesToNotesCommand toNotesCommand;
    final private CategoryToCategoryCommand toCategoryCommand;
    final private IngredientToIngredientCommand toIngredientCommand;

    public RecipeToRecipeCommand(NotesToNotesCommand toNotesCommand,
                                 CategoryToCategoryCommand toCategoryCommand,
                                 IngredientToIngredientCommand toIngredientCommand) {
        this.toNotesCommand = toNotesCommand;
        this.toCategoryCommand = toCategoryCommand;
        this.toIngredientCommand = toIngredientCommand;
    }

    @Synchronized
    @Override
    public RecipeCommand convert(Recipe source) {

        if (source == null)
            return null;

        final RecipeCommand recipeCommand = new RecipeCommand();

        recipeCommand.setId(source.getId());
        recipeCommand.setNotes(toNotesCommand.convert(source.getNote()));
        recipeCommand.setTitle(source.getTitle());
        recipeCommand.setUrl(source.getUrl());
        recipeCommand.setSource(source.getSource());
        recipeCommand.setServingPeople(source.getServingPeople());
        recipeCommand.setDirections(source.getDirections());
        recipeCommand.setPrepTimeMinutes(source.getPrepTimeMinutes());
        recipeCommand.setCookTimeMinutes(source.getCookTimeMinutes());
        recipeCommand.setDifficulty(source.getDifficulty());
        recipeCommand.setImage(source.getImage());

        if (source.getCategories() == null && !source.getCategories().isEmpty()) {
            source.getCategories().forEach(a-> recipeCommand.getCategories().add(toCategoryCommand.convert(a)));
        }

        if (source.getIngredients() != null && !source.getIngredients().isEmpty()){
            source.getIngredients().forEach(a->recipeCommand.getIngredients().add(toIngredientCommand.convert(a)));
        }

        return recipeCommand;
    }
}
