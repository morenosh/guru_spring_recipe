package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"/recipe/{id}/show"})
    String showById(@PathVariable String id, Model model) {
        var recipe = recipeService.findById(Long.parseLong(id));
        model.addAttribute("recipe", recipe);
        return "recipe/show";
    }

    @GetMapping("/recipe/new")
    String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
        var savedRecipeCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe/" + savedRecipeCommand.getId() + "/show/";
    }

    @GetMapping("/recipe/{id}/update")
    String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findRecipeCommandById(Long.parseLong(id)));

        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    String deleteById(@PathVariable String id) {

        log.debug("Deleting id: " + id);

        var longId = Long.parseLong(id);
        recipeService.deleteById(longId);

        return "redirect:/";
    }
}
