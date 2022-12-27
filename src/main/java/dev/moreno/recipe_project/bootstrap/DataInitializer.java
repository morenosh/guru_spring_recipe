package dev.moreno.recipe_project.bootstrap;

import dev.moreno.recipe_project.domains.*;
import dev.moreno.recipe_project.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;

@Slf4j
@Component
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("start bootstrap data");
        loadPerfectGuacamole();
        log.debug("perfect guacamole loaded.");
    }

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public DataInitializer(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private void loadPerfectGuacamole() {
        var r = new Recipe();

        var category = categoryRepository.findByDescription("Mexican").orElse(null);
        var setOfCategory = new HashSet<Category>();
        setOfCategory.add(category);
        r.setCategories(setOfCategory);
        r.setTitle("Best Guacamole");

        try {
            var url = getClass().getClassLoader().getResource("/static/images/perfect_quacamole.webp");
            if (url != null){
                var uri = url.toURI();
                var bytes = Files.readAllBytes(Path.of(uri));
                var bBytes = new Byte[bytes.length];
                for (int i = 0; i < bytes.length; i++) {
                    bBytes[i] = bytes[i];
                }
                r.setImage(bBytes);
            }
        } catch (URISyntaxException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        r.setDifficulty(Difficulty.EASY);
        r.setCookTimeMinutes(10L);
        r.setDirections("1.Cut the avocado. 2.Mash the avocado flesh 3.Add the remaining ingredients to taste 4.Serve immediately");
        r.setPrepTimeMinutes(10L);
        r.setServingPeople(((short) 4));
        r.setSource("source?");
        r.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/#toc-ingredients-for-easy-guacamole");

        var note = new Notes();
        note.setNotes("e careful handling chilis! If using, it's best to wear food-safe gloves. If no gloves are available, wash your hands thoroughly after handling, and do not touch your eyes or the area near your eyes for several hours afterwards.");
        note.setRecipe(r);

        var ripeAvocados = new Ingredient();
        var nothingUnitOfMeasure = unitOfMeasureRepository.findByUom("").orElse(null);
        ripeAvocados.setUnitOfMeasure(nothingUnitOfMeasure);
        ripeAvocados.setAmount(2f);
        ripeAvocados.setDescription("ripe avocados");
        ripeAvocados.setRecipe(r);

        var salt = new Ingredient();
        var tableSpoon = unitOfMeasureRepository.findByUom("Tablespoon").orElse(null);
        salt.setRecipe(r);
        salt.setAmount(0.25f);
        salt.setUnitOfMeasure(tableSpoon);
        salt.setDescription("kosher salt, plus more to taste");

        var lime = new Ingredient();
        lime.setRecipe(r);
        lime.setAmount(1f);
        lime.setUnitOfMeasure(tableSpoon);
        lime.setDescription("fresh lime or lemon juice");

        var onion = new Ingredient();
        onion.setRecipe(r);
        onion.setAmount(3f);
        onion.setUnitOfMeasure(tableSpoon);
        onion.setDescription("minced red onion or thinly sliced green onion");

        var serrano = new Ingredient();
        serrano.setRecipe(r);
        serrano.setAmount(2f);
        serrano.setUnitOfMeasure(nothingUnitOfMeasure);
        serrano.setDescription("serrano (or jalapeño) chilis, stems and seeds removed, minced");

        var cilantro = new Ingredient();
        cilantro.setRecipe(r);
        cilantro.setAmount(2f);
        cilantro.setUnitOfMeasure(tableSpoon);
        cilantro.setDescription("cilantro (leaves and tender stems), finely chopped");

        r.setNote(note);
        r.getIngredients().add(ripeAvocados);
        r.getIngredients().add(salt);
        r.getIngredients().add(lime);
        r.getIngredients().add(onion);
        r.getIngredients().add(serrano);
        r.getIngredients().add(cilantro);
        recipeRepository.save(r);
    }


}
