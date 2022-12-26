package dev.moreno.recipe_project.services;

import dev.moreno.recipe_project.commands.UnitOfMeasureCommand;
import dev.moreno.recipe_project.converters.UnitOfMeasureToUnitOfMeasureCommand;
import dev.moreno.recipe_project.domains.UnitOfMeasure;
import dev.moreno.recipe_project.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService{

    final private UnitOfMeasureRepository unitOfMeasureRepository;
    final private UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommand toUnitOfMeasureCommand) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.toUnitOfMeasureCommand = toUnitOfMeasureCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUnitOfMeasures() {
        return unitOfMeasureRepository.findAll()
                .stream().map(toUnitOfMeasureCommand::convert)
                .collect(Collectors.toSet());
    }
}
