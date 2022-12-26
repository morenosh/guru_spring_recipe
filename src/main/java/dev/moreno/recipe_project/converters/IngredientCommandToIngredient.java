package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.domains.Ingredient;
import dev.moreno.recipe_project.domains.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    final private UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure;

    public IngredientCommandToIngredient(UnitOfMeasureCommandToUnitOfMeasure toUnitOfMeasure) {
        this.toUnitOfMeasure = toUnitOfMeasure;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {

        if (source == null) return null;

        final Ingredient ingredient = new Ingredient();

        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setUnitOfMeasure(toUnitOfMeasure.convert(source.getUnitOfMeasure()));
        if(source.getRecipeId() != null){
            Recipe recipe = Recipe.builder().id(source.getRecipeId()).ingredients(new HashSet<>()).build();
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

        return ingredient;
    }
}
