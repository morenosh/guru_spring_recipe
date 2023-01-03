package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.ArrayUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {


    final private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(long recipeId, MultipartFile file) {
        var recipeOptional = recipeRepository.findById(recipeId);
        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("recipe not available id = " + recipeId);
        }

        var recipe = recipeOptional.get();
        try {
            var bytesFileContent = file.getBytes();
            var fileBytes = new Byte[bytesFileContent.length];
            for (int i = 0; i <bytesFileContent.length; i++) {
                fileBytes[i] = bytesFileContent[i];
            }
            recipe.setImage(fileBytes);
            recipeRepository.save(recipe);
        } catch (IOException e) {
            //todo do better handling
            log.debug("exception happened" + e.getMessage());
            e.printStackTrace();
        }


    }
}
