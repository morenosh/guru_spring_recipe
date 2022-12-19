package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.services.IngredientService;
import dev.moreno.recipe_project.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    final private RecipeService recipeService;
    final private IngredientService ingredientService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    String listIngredients(@PathVariable String recipeId, Model model) {

        var id = Long.parseLong(recipeId);
        var recipeCommand = recipeService.findRecipeCommandById(id);

        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/show")
    String showRecipeIngredient(@PathVariable String recipeId,
                                @PathVariable String id,
                                Model model){
        var recipeIdL = Long.parseLong(recipeId);
        var ingredientId = Integer.parseInt(id);

        var ingredientCommand = ingredientService.findCommandByIdAndRecipeId(ingredientId, recipeIdL);
        model.addAttribute("ingredient", ingredientCommand);

        return "/recipe/ingredient/show";
    }
}
