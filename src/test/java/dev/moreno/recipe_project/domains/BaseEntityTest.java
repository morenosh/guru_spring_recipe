package dev.moreno.recipe_project.domains;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

class BaseEntityTest {

    private BaseEntity<Integer> baseEntity;

    @BeforeEach
    public void setUp(){
        baseEntity = (BaseEntity<Integer>)Mockito.mock(BaseEntity.class, Mockito.CALLS_REAL_METHODS);
        ReflectionTestUtils.setField(baseEntity, "id", 10);
    }

    @Test
    void getId() {
        Assertions.assertEquals(baseEntity.getId(), 10);
    }
}