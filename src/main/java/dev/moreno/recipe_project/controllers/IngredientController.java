package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.services.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    final private RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredients")
    String listIngredients(@PathVariable String recipeId, Model model) {

        var id = Long.parseLong(recipeId);
        var recipeCommand = recipeService.findRecipeCommandById(id);

        model.addAttribute("recipe", recipeCommand);

        return "recipe/ingredient/list";
    }
}
