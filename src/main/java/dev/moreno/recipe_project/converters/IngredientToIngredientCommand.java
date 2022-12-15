package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.domains.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    final private UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public IngredientToIngredientCommand(UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient source) {

        if (source == null)
            return null;

        final IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setId(source.getId());
        ingredientCommand.setDescription(source.getDescription());
        ingredientCommand.setAmount(source.getAmount());
        ingredientCommand.setUnitOfMeasure(toUnitOfMeasureCommand.convert(source.getUnitOfMeasure()));

        return ingredientCommand;
    }
}
