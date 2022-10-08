package dev.moreno.recipe_project.domains;

import javax.persistence.*;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity<T extends Number> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    T id;

    public T getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;

        if (that.id == null || id == null) return false;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
