package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.domains.Recipe;
import dev.moreno.recipe_project.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class RecipeImpl implements RecipeService{
    final private RecipeRepository recipeRepo;

    public RecipeImpl(RecipeRepository recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    @Override
    public Set<Recipe> getRecipes(){
        var recipeSet = new HashSet<Recipe>();
        recipeRepo.findAll().iterator().forEachRemaining(recipeSet::add);
        return recipeSet;
    }
}
