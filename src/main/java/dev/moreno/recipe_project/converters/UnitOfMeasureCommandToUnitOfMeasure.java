package dev.moreno.recipe_project.converters;

import dev.moreno.recipe_project.commands.UnitOfMeasureCommand;
import dev.moreno.recipe_project.domains.UnitOfMeasure;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class UnitOfMeasureCommandToUnitOfMeasure implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(UnitOfMeasureCommand source) {

        if (source == null)
            return null;

        final UnitOfMeasure unitOfMeasure = new UnitOfMeasure();

        unitOfMeasure.setId(source.getId());
        unitOfMeasure.setUom(source.getDescription());

        return unitOfMeasure;
    }
}
