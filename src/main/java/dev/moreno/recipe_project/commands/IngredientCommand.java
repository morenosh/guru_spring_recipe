package dev.moreno.recipe_project.commands;

import dev.moreno.recipe_project.domains.UnitOfMeasure;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {
    private Integer id;
    private Long recipeId;
    private String description;
    private Float amount;
    private UnitOfMeasureCommand unitOfMeasure;

}
