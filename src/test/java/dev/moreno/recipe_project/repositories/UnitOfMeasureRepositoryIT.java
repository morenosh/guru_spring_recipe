package dev.moreno.recipe_project.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@SuppressWarnings("NewClassNamingConvention")
@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void findAll() {
        var siz = (long) unitOfMeasureRepository.findAll().size();
        Assertions.assertEquals(siz, 6);
    }

    @Test
    void findByUom() {
        var uom = unitOfMeasureRepository.findByUom("Ounce");
        Assertions.assertTrue(uom.isPresent());
        Assertions.assertEquals(uom.get().getUom(), "Ounce");
    }
    @Test
    void findByUomEmptyName() {
        var uom = unitOfMeasureRepository.findByUom("");
        Assertions.assertTrue(uom.isPresent());
        Assertions.assertEquals(uom.get().getUom(), "");
    }

}