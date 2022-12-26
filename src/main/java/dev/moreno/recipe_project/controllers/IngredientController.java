package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.commands.IngredientCommand;
import dev.moreno.recipe_project.services.IngredientService;
import dev.moreno.recipe_project.services.RecipeService;
import dev.moreno.recipe_project.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {

    final private RecipeService recipeService;
    final private IngredientService ingredientService;
    final private UnitOfMeasureService unitOfMeasureService;


    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
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
                                Model model) {
        var recipeIdL = Long.parseLong(recipeId);
        var ingredientId = Integer.parseInt(id);

        var ingredientCommand = ingredientService.findCommandByIdAndRecipeId(ingredientId, recipeIdL);
        model.addAttribute("ingredient", ingredientCommand);

        return "/recipe/ingredient/show";
    }


    @GetMapping
    @RequestMapping("/recipe/{recipeId}/ingredient/{id}/update")
    String updateRecipeIngredient(@PathVariable String recipeId,
                                  @PathVariable String id,
                                  Model model) {
        var ingredientCommand =
                ingredientService.findCommandByIdAndRecipeId(Integer.parseInt(id), Long.parseLong(recipeId));
        model.addAttribute("ingredient", ingredientCommand);

        model.addAttribute("uomList", unitOfMeasureService.listAllUnitOfMeasures());

        return "recipe/ingredient/ingredientform";
    }

    @PostMapping
    @RequestMapping("/recipe/{recipeId}/ingredient")
    String savedRecipeIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        var savedIngredientCommand = ingredientService.saveIngredientCommand(ingredientCommand);
        log.debug("saved receipe id:" + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedIngredientCommand.getId());
        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() + "/ingredient/" + savedIngredientCommand.getId() + "/show";

    }

}
