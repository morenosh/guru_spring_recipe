package dev.moreno.recipe_project.controllers;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.services.ImageService;
import dev.moreno.recipe_project.services.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class ImageControllerTest {

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @InjectMocks
    ImageController imageController;

    @Test
    void getImageForm() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        RecipeCommand command = new RecipeCommand();
        command.setId(1L);

        //when
        Mockito.when(recipeService.findRecipeCommandById(Mockito.anyLong())).thenReturn(command);

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/recipe/1/image"))
            .andExpect(MockMvcResultMatchers.view().name("recipe/imageuploadform"))
            .andExpect(MockMvcResultMatchers.model().attributeExists("recipe"))
            .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(recipeService, Mockito.times(1)).findRecipeCommandById(1L);
    }

    @Test
    void handleImagePost() throws Exception {
        //given
        var mockMvc = MockMvcBuilders.standaloneSetup(imageController).build();
        MockMultipartFile multipartFile =
            new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "moreno is coding" .getBytes());

        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/1/image").file(multipartFile))
            .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
            .andExpect(MockMvcResultMatchers.header().string("Location", "/recipe/1/show"));

        Mockito.verify(imageService, Mockito.times(1)).saveImageFile(Mockito.anyLong(), Mockito.any());
    }

    @Test
    void handleImagePostNumberFormatException() throws Exception {
        //given
        var mockMvc =
            MockMvcBuilders.standaloneSetup(imageController).setControllerAdvice(ControllerExceptionHandler.class).build();
        MockMultipartFile multipartFile =
            new MockMultipartFile("imagefile", "testing.txt",
                "text/plain", "moreno is coding" .getBytes());
        //when

        //then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/recipe/a/image").file(multipartFile))
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.view().name("/400error.html"));

    }
}
