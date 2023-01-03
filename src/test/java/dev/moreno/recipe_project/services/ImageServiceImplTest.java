package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ImageServiceImplTest {

    @Mock
    RecipeRepository recipeRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    void saveImageFile() {
        //given
        var recipeId = 1L;
        var imageContent = ("content of " + "image!").getBytes();
        var file = new MockMultipartFile("imageFile", "testing.txt", "text.plain", imageContent);
        var recipe = Recipe.builder().id(recipeId).build();
        Mockito.when(recipeRepository.save(Mockito.any())).thenReturn(recipe);
        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);
        Mockito.when(recipeRepository.findById(recipeId)).thenReturn(Optional.of(recipe));

        //when
        imageService.saveImageFile(recipeId, file);

        //then
        Mockito.verify(recipeRepository, Mockito.times(1)).save(recipeArgumentCaptor.capture());
        Assertions.assertEquals(recipeArgumentCaptor.getValue().getImage().length, imageContent.length);
        Assertions.assertEquals(recipeArgumentCaptor.getValue().getImage()[0].byteValue(), imageContent[0]);
        Assertions.assertEquals(recipeArgumentCaptor.getValue().getImage()[1].byteValue(), imageContent[1]);
    }
}
