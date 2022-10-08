package dev.moreno.recipe_project.repositories;

import dev.moreno.recipe_project.domains.Category;
import dev.moreno.recipe_project.domains.UnitOfMeasure;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.Set;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Short> {
    Set<UnitOfMeasure> findAll();
    Optional<UnitOfMeasure> findByUom(String uom);
}

