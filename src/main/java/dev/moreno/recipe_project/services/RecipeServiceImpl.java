package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.RecipeCommand;
import dev.moreno.recipe_project.converters.RecipeCommandToRecipe;
import dev.moreno.recipe_project.converters.RecipeToRecipeCommand;
import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    final private RecipeRepository recipeRepo;

    final private RecipeCommandToRecipe toRecipe;
    final private RecipeToRecipeCommand toRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepo,
                             RecipeCommandToRecipe toRecipe,
                             RecipeToRecipeCommand toRecipeCommand) {
        this.recipeRepo = recipeRepo;
        this.toRecipe = toRecipe;
        this.toRecipeCommand = toRecipeCommand;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("in getRecipes service in RecipeImpl");
        var recipeSet = new HashSet<Recipe>();
        recipeRepo.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }

    @Override
    public Recipe findById(Long id) {
        var optional = recipeRepo.findById(id);
        return optional.orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        var detachedRecipe = toRecipe.convert(recipeCommand);
        assert detachedRecipe != null;
        var savedRecipe = recipeRepo.save(detachedRecipe);
        log.debug("Saved Recipe: " + detachedRecipe.getId());
        return toRecipeCommand.convert(savedRecipe);
    }

    @Override
    public RecipeCommand findRecipeCommandById(Long id) {
        return toRecipeCommand.convert(findById(id));
    }

}
