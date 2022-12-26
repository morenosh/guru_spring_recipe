package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.UnitOfMeasureCommand;
import dev.moreno.recipe_project.domains.UnitOfMeasure;
import org.springframework.stereotype.Service;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> listAllUnitOfMeasures();
}
