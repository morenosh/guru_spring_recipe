package dev.moreno.recipe_project.repositories;

import dev.moreno.recipe_project.domains.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface CategoryRepository extends CrudRepository<Category, Short> {
    Set<Category> findAll();
    Optional<Category> findByDescription(String description);
}
