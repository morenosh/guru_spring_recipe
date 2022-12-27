package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.services.ImageService;
import dev.moreno.recipe_project.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@Slf4j
public class ImageController {

    final private RecipeService recipeService;
    final private ImageService imageService;

    public ImageController(RecipeService recipeService, ImageService imageService) {
        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    String showUploadForm(@PathVariable String recipeId, Model model){
        var recipeIdLong = Long.parseLong(recipeId);
        var recipeCommand = recipeService.findRecipeCommandById(recipeIdLong);
        model.addAttribute("recipe", recipeCommand);

        return "recipe/imageuploadform";
    }

    @PostMapping("/recipe/{recipeId}/image")
    String handleImagePost(@PathVariable String recipeId, @RequestParam("imagefile") MultipartFile file){
        imageService.saveImageFile(Long.parseLong(recipeId), file);
        return "redirect:/recipe/" + recipeId + "/show";
    }
}
