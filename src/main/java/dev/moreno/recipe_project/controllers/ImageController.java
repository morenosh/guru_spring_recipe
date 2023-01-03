package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.services.ImageService;
import dev.moreno.recipe_project.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

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

    @GetMapping("/recipe/{recipeId}/recipeimage")
    void renderRecipeImage(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        var recipeIdLong = Long.parseLong(recipeId);
        var recipeCommand = recipeService.findRecipeCommandById(recipeIdLong);
        var imageBytes = recipeCommand.getImage();
        if (imageBytes == null) {
            //todo do better handling
            return;
        }

        var bytesImageUnboxed = new byte[imageBytes.length];
        for (int i = 0; i < imageBytes.length; i++) {
            bytesImageUnboxed[i] = imageBytes[i];
        }
        response.setContentType("image/jpeg");
        IOUtils.copy(new ByteArrayInputStream(bytesImageUnboxed), response.getOutputStream());
    }
}
